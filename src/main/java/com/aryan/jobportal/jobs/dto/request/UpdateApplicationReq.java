package com.aryan.jobportal.jobs.dto.request;

import com.aryan.jobportal.common.enums.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationReq {

    private ApplicationStatus newStatus;

    private String remarks;
}
