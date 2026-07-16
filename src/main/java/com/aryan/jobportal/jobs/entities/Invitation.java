package com.aryan.jobportal.jobs.entities;

import com.aryan.jobportal.common.entities.Base;
import com.aryan.jobportal.common.enums.ApplicationStatus;
import com.aryan.jobportal.common.enums.InvitationStatus;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"job_id", "job_seeker_id"}
        )
)
public class Invitation extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Job job;

    @ManyToOne
    private JobSeeker jobSeeker;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.PENDING;

    private Instant respondedAt;

    @Column(nullable = false)
    private Instant expiresAt;
}
