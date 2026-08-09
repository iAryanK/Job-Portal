package com.aryan.jobportal.jobs.controllers;

import com.aryan.jobportal.jobs.dto.request.CreateJobReq;
import com.aryan.jobportal.jobs.dto.request.FindJobsFilterReq;
import com.aryan.jobportal.jobs.dto.request.PatchJobReq;
import com.aryan.jobportal.jobs.dto.response.JobResp;
import com.aryan.jobportal.jobs.dto.response.JobSummaryResp;
import com.aryan.jobportal.jobs.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs/core")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResp> createJob(@RequestBody CreateJobReq jobReq) {
        JobResp job = jobService.createJob(jobReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchJobById(@PathVariable UUID id,
                                                @RequestBody PatchJobReq patchJobReq) {
        jobService.patchJobById(id, patchJobReq);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResp> getJobById(@PathVariable UUID id) {
        JobResp job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping
    public ResponseEntity<Page<JobSummaryResp>> findJobs(
            @ModelAttribute FindJobsFilterReq filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<JobSummaryResp> jobs = jobService.findJobs(filter, pageable);
        return ResponseEntity.ok(jobs);
    }
}
