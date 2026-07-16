package com.aryan.jobportal.jobs.dto.request;

import com.aryan.jobportal.common.enums.InvitationStatus;
import lombok.Data;

@Data
public class UpdateInvitationReq {

    private InvitationStatus status;
}
