package com.aryan.jobportal.jobs.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.employer.entities.Employer;
import com.aryan.jobportal.employer.repositories.EmployerRepository;
import com.aryan.jobportal.jobs.dto.request.CreateJobReq;
import com.aryan.jobportal.jobs.dto.request.PatchJobReq;
import com.aryan.jobportal.jobs.dto.response.JobResp;
import com.aryan.jobportal.jobs.dto.response.JobSummaryResp;
import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobs.mapper.UpdateJobMapper;
import com.aryan.jobportal.jobs.repositories.JobRepository;
import com.aryan.jobportal.jobs.services.JobService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

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
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        return modelMapper.map(job, JobResp.class);
    }

    @Override
    public List<JobSummaryResp> findJobs() {
        return List.of();
    }
}
