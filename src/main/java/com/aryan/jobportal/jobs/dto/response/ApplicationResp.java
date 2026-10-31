package com.aryan.jobportal.jobs.dto.response;

import com.aryan.jobportal.common.enums.ApplicationStatus;
import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ApplicationResp {

    private UUID id;

    private String message;

    private ApplicationStatus status;

    private Job job;

    private JobSeeker jobSeeker;

    private Instant createdAt;
}
