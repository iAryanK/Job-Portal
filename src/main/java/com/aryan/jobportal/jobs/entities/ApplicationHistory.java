package com.aryan.jobportal.jobs.entities;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.entities.Base;
import com.aryan.jobportal.common.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationHistory extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id")
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus toStatus;

    @Column(length = 200)
    private String remarks;
}
