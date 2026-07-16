package com.aryan.jobportal.jobs.dto.request;

import com.aryan.jobportal.jobs.entities.Job;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import lombok.Data;

@Data
public class CreateApplicationReq {

    private Job job;

    private String message;

    private JobSeeker jobSeeker;
}
