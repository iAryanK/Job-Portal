package com.aryan.jobportal.jobs.entities;

import com.aryan.jobportal.common.entities.Base;
import com.aryan.jobportal.common.enums.ApplicationStatus;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Application extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Job job;

    @Column(length = 500)
    private String message;

    @ManyToOne
    private JobSeeker jobSeeker;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;
}
