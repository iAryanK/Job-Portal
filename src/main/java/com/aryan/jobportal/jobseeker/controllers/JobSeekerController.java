package com.aryan.jobportal.jobseeker.controllers;

import com.aryan.jobportal.jobseeker.dto.request.CreateJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.request.PatchJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerResp;
import com.aryan.jobportal.jobseeker.services.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobseeker/core")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerResp> findJobSeekerById(@PathVariable UUID id) {
        JobSeekerResp jobSeeker = jobSeekerService.findJobSeekerById(id);
        return ResponseEntity.ok(jobSeeker);
    }

    @PostMapping
    public ResponseEntity<JobSeekerResp> createJobSeeker(@RequestBody CreateJobSeekerReq createJobSeekerReq) {
        JobSeekerResp jobSeeker = jobSeekerService.createJobSeeker(createJobSeekerReq);
        return ResponseEntity.ok(jobSeeker);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JobSeekerResp> patchJobSeekerById(@PathVariable UUID id,
                                                            @RequestBody PatchJobSeekerReq patchjobSeekerReq) {
        JobSeekerResp jobSeeker = jobSeekerService.patchJobSeekerById(id, patchjobSeekerReq);
        return ResponseEntity.ok(jobSeeker);
    }
}
