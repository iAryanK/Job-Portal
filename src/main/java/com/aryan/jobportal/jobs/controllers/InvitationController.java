package com.aryan.jobportal.jobs.controllers;

import com.aryan.jobportal.jobs.dto.request.InvitationReq;
import com.aryan.jobportal.jobs.dto.request.UpdateInvitationReq;
import com.aryan.jobportal.jobs.dto.response.InvitationResp;
import com.aryan.jobportal.jobs.services.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationResp> inviteJobSeeker(@RequestBody InvitationReq invitationReq) {
        InvitationResp invitationResp = invitationService.inviteJobSeeker(invitationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationResp);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<List<InvitationResp>> getInvitationsOfJob(@PathVariable UUID jobId) {
        List<InvitationResp> invitations = invitationService.getInvitationsOfJob(jobId);
        return ResponseEntity.ok(invitations);
    }

    @GetMapping
    public ResponseEntity<List<InvitationResp>> getInvitationsOfJobSeeker() {
        List<InvitationResp> invitations = invitationService.getInvitationsOfJobSeeker();
        return ResponseEntity.ok(invitations);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateInviteStatus(@PathVariable UUID id,
                                                   @RequestBody UpdateInvitationReq updateInvitationReq) {
        invitationService.updateInviteStatus(id, updateInvitationReq);
        return ResponseEntity.noContent().build();
    }
}
