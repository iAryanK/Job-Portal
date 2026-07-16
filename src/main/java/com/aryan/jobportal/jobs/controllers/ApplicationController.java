package com.aryan.jobportal.jobs.controllers;

import com.aryan.jobportal.common.enums.ApplicationStatus;
import com.aryan.jobportal.jobs.dto.request.ApplicationReq;
import com.aryan.jobportal.jobs.dto.request.UpdateApplicationReq;
import com.aryan.jobportal.jobs.dto.response.ApplicationResp;
import com.aryan.jobportal.jobs.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/{jobId}")
    public ResponseEntity<List<ApplicationResp>> getApplicationsOfJob(@PathVariable UUID jobId) {
        List<ApplicationResp> applications = applicationService.getApplicationsOfJob(jobId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/jobseeker/{jobSeekerId}")
    public ResponseEntity<List<ApplicationResp>> getApplicationsOfJobSeeker(@PathVariable UUID jobSeekerId) {
        List<ApplicationResp> application = applicationService.getApplicationsOfJobSeeker(jobSeekerId);
        return ResponseEntity.ok(application);
    }

    @PostMapping
    public ResponseEntity<ApplicationResp> createApplication(@RequestBody ApplicationReq applicationReq) {
        ApplicationResp application = applicationService.createApplication(applicationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @PatchMapping("/{applicationId}")
    public ResponseEntity<ApplicationResp> updateApplicationStatus(@PathVariable UUID applicationId,
                                                                   @RequestBody UpdateApplicationReq updateApplicationReq) {
        applicationService.updateApplicationStatus(applicationId, updateApplicationReq);
        return ResponseEntity.noContent().build();
    }
}
