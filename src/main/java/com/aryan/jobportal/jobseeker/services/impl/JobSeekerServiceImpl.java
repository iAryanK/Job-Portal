package com.aryan.jobportal.jobseeker.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.jobseeker.dto.request.CreateJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.request.FindJobSeekersFilterReq;
import com.aryan.jobportal.jobseeker.dto.request.PatchJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerResp;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerSummaryResp;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import com.aryan.jobportal.jobseeker.mapper.UpdateJobSeekerMapper;
import com.aryan.jobportal.jobseeker.repositories.JobSeekerRepository;
import com.aryan.jobportal.jobseeker.services.JobSeekerService;
import com.aryan.jobportal.jobseeker.specifications.JobSeekerSpecifications;
import com.aryan.jobportal.common.exceptions.DuplicateRequestException;
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
public class JobSeekerServiceImpl implements JobSeekerService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt",
            "updatedAt",
            "name",
            "experienceYears",
            "experienceMonths",
            "passingYear",
            "cgpa",
            "city",
            "state",
            "country"
    );

    private final JobSeekerRepository jobSeekerRepository;
    private final UpdateJobSeekerMapper updateJobSeekerMapper;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Page<JobSeekerSummaryResp> findJobSeekers(FindJobSeekersFilterReq filter, Pageable pageable) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.EMPLOYER.equals(appUser.getRole())) {
            throw new AccessDeniedException("You are not authorized to browse job seekers");
        }

        FindJobSeekersFilterReq safeFilter = filter != null ? filter : new FindJobSeekersFilterReq();
        Pageable sanitizedPageable = sanitizePageable(pageable);
        Specification<JobSeeker> specification = JobSeekerSpecifications.withFilter(safeFilter);

        return jobSeekerRepository.findAll(specification, sanitizedPageable)
                .map(jobSeeker -> modelMapper.map(jobSeeker, JobSeekerSummaryResp.class));
    }

    @Override
    public JobSeekerResp findJobSeekerById(UUID id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(jobSeeker, JobSeekerResp.class);
    }

    @Override
    public JobSeekerResp createJobSeeker(CreateJobSeekerReq createJobSeekerReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.JOBSEEKER.equals(appUser.getRole()))
            throw new AccessDeniedException("Invalid request");
        if (jobSeekerRepository.existsById(appUser.getId())) {
            throw new DuplicateRequestException("Profile already exists");
        }

        JobSeeker jobSeeker = modelMapper.map(createJobSeekerReq, JobSeeker.class);

        jobSeeker.setAppUser(appUser);
        jobSeekerRepository.save(jobSeeker);

        return modelMapper.map(jobSeeker, JobSeekerResp.class);
    }

    @Override
    public JobSeekerResp patchJobSeekerById(UUID id, PatchJobSeekerReq patchjobSeekerReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JobSeeker jobSeeker = jobSeekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        if (!jobSeeker.getAppUser().getId().equals(appUser.getId())) {
            throw new AccessDeniedException("You are not authorized to update this business");
        }

        updateJobSeekerMapper.update(patchjobSeekerReq, jobSeeker);

        jobSeeker = jobSeekerRepository.save(jobSeeker);
        return modelMapper.map(jobSeeker, JobSeekerResp.class);
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
