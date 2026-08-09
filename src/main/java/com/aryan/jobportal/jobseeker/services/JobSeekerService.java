package com.aryan.jobportal.jobseeker.services;

import com.aryan.jobportal.jobseeker.dto.request.CreateJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.request.FindJobSeekersFilterReq;
import com.aryan.jobportal.jobseeker.dto.request.PatchJobSeekerReq;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerResp;
import com.aryan.jobportal.jobseeker.dto.response.JobSeekerSummaryResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface JobSeekerService {
    Page<JobSeekerSummaryResp> findJobSeekers(FindJobSeekersFilterReq filter, Pageable pageable);

    JobSeekerResp findJobSeekerById(UUID id);

    JobSeekerResp createJobSeeker(CreateJobSeekerReq createJobSeekerReq);

    JobSeekerResp patchJobSeekerById(UUID id, PatchJobSeekerReq patchjobSeekerReq);
}
