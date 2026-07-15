package com.aryan.jobportal.jobseeker.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.jobseeker.dto.request.CreateJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.request.PatchJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerResp;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import com.aryan.jobportal.jobseeker.mapper.UpdateJobSeekerMapper;
import com.aryan.jobportal.jobseeker.repositories.JobSeekerRepository;
import com.aryan.jobportal.jobseeker.services.JobSeekerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final ModelMapper modelMapper;
    private final UpdateJobSeekerMapper updateJobSeekerMapper;

    @Override
    public JobSeekerResp findJobSeekerById(UUID id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(jobSeeker, JobSeekerResp.class);
    }

    @Override
    public JobSeekerResp createJobSeeker(CreateJobSeekerReq createJobSeekerReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
