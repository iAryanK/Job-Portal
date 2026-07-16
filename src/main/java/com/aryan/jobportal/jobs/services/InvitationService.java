package com.aryan.jobportal.jobs.services;

import com.aryan.jobportal.jobs.dto.request.InvitationReq;
import com.aryan.jobportal.jobs.dto.request.UpdateInvitationReq;
import com.aryan.jobportal.jobs.dto.response.InvitationResp;

import java.util.List;
import java.util.UUID;

public interface InvitationService {
    InvitationResp inviteJobSeeker(InvitationReq invitationReq);

    List<InvitationResp> getInvitationsOfJobSeeker();

    void updateInviteStatus(UUID id, UpdateInvitationReq updateInvitationReq);
}
