package com.aryan.jobportal.jobs.services;

import com.aryan.jobportal.jobs.dto.request.ApplicationReq;
import com.aryan.jobportal.jobs.dto.request.UpdateApplicationReq;
import com.aryan.jobportal.jobs.dto.response.ApplicationResp;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    List<ApplicationResp> getApplicationsOfJob(UUID jobId);

    List<ApplicationResp> getApplicationsOfJobSeeker(UUID jobSeekerId);

    ApplicationResp createApplication(ApplicationReq applicationReq);

    void updateApplicationStatus(UUID applicationId, UpdateApplicationReq updateApplicationReq);
}
