package com.aryan.jobportal.jobs.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import com.aryan.jobportal.common.enums.ApplicationStatus;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.common.exceptions.UnauthorizedException;
import com.aryan.jobportal.jobs.dto.request.ApplicationReq;
import com.aryan.jobportal.jobs.dto.request.UpdateApplicationReq;
import com.aryan.jobportal.jobs.dto.response.ApplicationResp;
import com.aryan.jobportal.jobs.entities.Application;
import com.aryan.jobportal.jobs.entities.ApplicationHistory;
import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobs.repositories.ApplicationHistoryRepository;
import com.aryan.jobportal.jobs.repositories.ApplicationRepository;
import com.aryan.jobportal.jobs.repositories.JobRepository;
import com.aryan.jobportal.jobs.services.ApplicationService;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import com.aryan.jobportal.jobseeker.repositories.JobSeekerRepository;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ApplicationHistoryRepository applicationHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ApplicationResp> getApplicationsOfJob(UUID jobId) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        if (!job.getEmployer().getAppUser().getId().equals(appUser.getId()))
            throw new AccessDeniedException("You are not authorized to view applications");

        List<Application> applications = applicationRepository.findByJob_Id(jobId);
        return applications.stream()
                .map(application -> modelMapper.map(application, ApplicationResp.class))
                .toList();
    }

    @Override
    public List<ApplicationResp> getApplicationsOfJobSeeker(UUID jobSeekerId) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!jobSeeker.getAppUser().getId().equals(appUser.getId()))
            throw new AccessDeniedException("You are not authorized to view applications");

        List<Application> applications = applicationRepository.findByJobSeeker_Id(jobSeekerId);
        return applications.stream()
                .map(application -> modelMapper.map(application, ApplicationResp.class))
                .toList();
    }

    @Override
    public ApplicationResp createApplication(ApplicationReq applicationReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.JOBSEEKER.equals(appUser.getRole()))
            throw new UnauthorizedException("You are not authorized to apply for a job");

        JobSeeker jobSeeker = jobSeekerRepository.findById(applicationReq.getJobSeeker().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        boolean applicationAlreadyExists = applicationRepository.existsByJob_IdAndJobSeeker_Id(
                applicationReq.getJob().getId(), applicationReq.getJobSeeker().getId()
        );
        if (applicationAlreadyExists)
            throw new DuplicateRequestException("Already applied to this job");

        Application application = modelMapper.map(applicationReq, Application.class);
        application.setJobSeeker(jobSeeker);
        application.setStatus(ApplicationStatus.APPLIED);
        application = applicationRepository.save(application);
        return modelMapper.map(application, ApplicationResp.class);
    }

    @Override
    @Transactional
    public void updateApplicationStatus(UUID applicationId, UpdateApplicationReq updateApplicationReq) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!application.getJob().getEmployer().getId().equals(appUser.getId()))
            throw new UnauthorizedException("You are not allowed to perform this action");

        ApplicationStatus oldStatus = application.getStatus();

        application.setStatus(updateApplicationReq.getNewStatus());
        applicationRepository.save(application);

        ApplicationHistory history =
                ApplicationHistory.builder()
                        .application(application)
                        .fromStatus(oldStatus)
                        .toStatus(updateApplicationReq.getNewStatus())
                        .remarks(updateApplicationReq.getRemarks())
                        .build();
        applicationHistoryRepository.save(history);
    }
}
