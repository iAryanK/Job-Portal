package com.aryan.jobportal.jobs.services;

import com.aryan.jobportal.jobs.dto.request.CreateJobReq;
import com.aryan.jobportal.jobs.dto.request.FindJobsFilterReq;
import com.aryan.jobportal.jobs.dto.request.PatchJobReq;
import com.aryan.jobportal.jobs.dto.response.JobResp;
import com.aryan.jobportal.jobs.dto.response.JobSummaryResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface JobService {

    JobResp createJob(CreateJobReq jobReq);

    void patchJobById(UUID id, PatchJobReq patchJobReq);

    JobResp getJobById(UUID id);

    Page<JobSummaryResp> findJobs(FindJobsFilterReq filter, Pageable pageable);
}
