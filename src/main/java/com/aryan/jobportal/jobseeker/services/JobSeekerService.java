package com.aryan.jobportal.jobseeker.services;

import com.aryan.jobportal.jobseeker.dto.request.CreateJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.request.PatchJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerResp;

import java.util.UUID;

public interface JobSeekerService {
    JobSeekerResp findJobSeekerById(UUID id);

    JobSeekerResp createJobSeeker(CreateJobSeekerReq createJobSeekerReq);

    JobSeekerResp patchJobSeekerById(UUID id, PatchJobSeekerReq patchjobSeekerReq);
}
