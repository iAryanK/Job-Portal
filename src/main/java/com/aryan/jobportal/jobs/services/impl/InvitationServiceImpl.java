package com.aryan.jobportal.jobs.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.common.exceptions.UnauthorizedException;
import com.aryan.jobportal.jobs.dto.request.InvitationReq;
import com.aryan.jobportal.jobs.dto.request.UpdateInvitationReq;
import com.aryan.jobportal.jobs.dto.response.InvitationResp;
import com.aryan.jobportal.jobs.entities.Invitation;
import com.aryan.jobportal.jobs.repositories.InvitationRepository;
import com.aryan.jobportal.jobs.services.InvitationService;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import com.aryan.jobportal.jobseeker.repositories.JobSeekerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ModelMapper modelMapper;

    @Override
    public InvitationResp inviteJobSeeker(InvitationReq invitationReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!invitationReq.getJob().getEmployer().getId().equals(appUser.getId()))
            throw new UnauthorizedException("You are not allowed to invite users for this job");

        Invitation invitation = modelMapper.map(invitationReq, Invitation.class);
        invitation = invitationRepository.save(invitation);
        return modelMapper.map(invitation, InvitationResp.class);
    }

    @Override
    public List<InvitationResp> getInvitationsOfJobSeeker() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.JOBSEEKER.equals(appUser.getRole()))
            throw new UnauthorizedException("Invalid request");

        JobSeeker jobSeeker = jobSeekerRepository.findByAppUser_Id(appUser.getId());
        List<Invitation> invitations = invitationRepository.findByJobSeeker_Id(jobSeeker.getId());

        return invitations.stream()
                .map(invitation -> modelMapper.map(invitation, InvitationResp.class))
                .toList();
    }

    @Override
    @Transactional
    public void updateInviteStatus(UUID id, UpdateInvitationReq updateInvitationReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.JOBSEEKER.equals(appUser.getRole()))
            throw new UnauthorizedException("Invalid request");

        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));
        if (invitation.getExpiresAt().isBefore(Instant.now()))
            throw new IllegalStateException("Invitation is expired");

        invitation.setStatus(updateInvitationReq.getStatus());
        invitation.setRespondedAt(Instant.now());
        invitationRepository.save(invitation);
    }
}