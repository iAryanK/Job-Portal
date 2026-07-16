package com.aryan.jobportal.jobs.dto.response;

import com.aryan.jobportal.common.enums.InvitationStatus;
import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class InvitationResp {

    private UUID id;

    private Job job;

    private JobSeeker jobSeeker;

    private InvitationStatus status;

    private Instant respondedAt;

    private Instant expiresAt;
}
