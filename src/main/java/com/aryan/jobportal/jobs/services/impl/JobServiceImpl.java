package com.aryan.jobportal.jobs.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.employer.entities.Employer;
import com.aryan.jobportal.employer.repositories.EmployerRepository;
import com.aryan.jobportal.jobs.dto.request.CreateJobReq;
import com.aryan.jobportal.jobs.dto.request.FindJobsFilterReq;
import com.aryan.jobportal.jobs.dto.request.PatchJobReq;
import com.aryan.jobportal.jobs.dto.response.JobResp;
import com.aryan.jobportal.jobs.dto.response.JobSummaryResp;
import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobs.mapper.UpdateJobMapper;
import com.aryan.jobportal.jobs.repositories.JobRepository;
import com.aryan.jobportal.jobs.services.JobService;
import com.aryan.jobportal.jobs.specifications.JobSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt",
            "updatedAt",
            "title",
            "minimumSalary",
            "maximumSalary",
            "minimumExperience",
            "maximumExperience",
            "applicationDeadline",
            "expiresAt",
            "applicationCount"
    );

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final ModelMapper modelMapper;
    private final UpdateJobMapper updateJobMapper;


    @Override
    public JobResp createJob(CreateJobReq jobReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.EMPLOYER.equals(appUser.getRole()))
            throw new AccessDeniedException("You are not authorized to create a job");

        Employer employer = employerRepository.findEmployerByAppUser_Id(appUser.getId());

        Job job = modelMapper.map(jobReq, Job.class);
        job.setEmployer(employer);
        job = jobRepository.save(job);

        return modelMapper.map(job, JobResp.class);
    }

    @Override
    @Transactional
    public void patchJobById(UUID id, PatchJobReq patchJobReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.EMPLOYER.equals(appUser.getRole()))
            throw new AccessDeniedException("You are not authorized to update this job");

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getEmployer().getAppUser().getId().equals(appUser.getId())) {
            throw new AccessDeniedException("You are not authorized to update this job");
        }

        updateJobMapper.update(patchJobReq, job);

        jobRepository.save(job);
    }

    @Override
    public JobResp getJobById(UUID id) {
        Job job = jobRepository.findWithEmployerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        return modelMapper.map(job, JobResp.class);
    }

    @Override
    @Transactional
    public Page<JobSummaryResp> findJobs(FindJobsFilterReq filter, Pageable pageable) {
        FindJobsFilterReq safeFilter = filter != null ? filter : new FindJobsFilterReq();
        Pageable sanitizedPageable = sanitizePageable(pageable);
        Specification<Job> specification = JobSpecifications.withFilter(safeFilter);

        return jobRepository.findAll(specification, sanitizedPageable)
                .map(job -> modelMapper.map(job, JobSummaryResp.class));
    }

    private Pageable sanitizePageable(Pageable pageable) {
        int page = pageable != null ? Math.max(pageable.getPageNumber(), 0) : 0;
        int size = pageable != null ? pageable.getPageSize() : 20;
        size = Math.min(Math.max(size, 1), 50);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (pageable != null && pageable.getSort().isSorted()) {
            List<Sort.Order> orders = pageable.getSort().stream()
                    .filter(order -> ALLOWED_SORT_FIELDS.contains(order.getProperty()))
                    .toList();
            if (!orders.isEmpty()) {
                sort = Sort.by(orders);
            }
        }

        return PageRequest.of(page, size, sort);
    }
}
