package com.aryan.jobportal.jobs.dto.request;

import com.aryan.jobportal.common.enums.InvitationStatus;
import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import lombok.Data;

import java.time.Instant;

@Data
public class InvitationReq {

    private Job job;

    private JobSeeker jobSeeker;

    private InvitationStatus status;

    private Instant expiresAt;
}
