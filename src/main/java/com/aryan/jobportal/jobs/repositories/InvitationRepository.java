package com.aryan.jobportal.jobs.repositories;

import com.aryan.jobportal.jobs.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
    List<Invitation> findByJobSeeker_Id(UUID id);

    List<Invitation> findByJob_Id(UUID jobId);
}
