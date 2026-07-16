package com.aryan.jobportal.jobs.services;

import com.aryan.jobportal.jobs.dto.request.CreateJobReq;
import com.aryan.jobportal.jobs.dto.request.PatchJobReq;
import com.aryan.jobportal.jobs.dto.response.JobResp;
import com.aryan.jobportal.jobs.dto.response.JobSummaryResp;

import java.util.List;
import java.util.UUID;

public interface JobService {

    JobResp createJob(CreateJobReq jobReq);

    void patchJobById(UUID id, PatchJobReq patchJobReq);

    JobResp getJobById(UUID id);

    List<JobSummaryResp> findJobs();
}
