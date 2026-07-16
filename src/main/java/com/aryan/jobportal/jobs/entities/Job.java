package com.aryan.jobportal.jobs.entities;

import com.aryan.jobportal.common.entities.Base;
import com.aryan.jobportal.common.enums.*;
import com.aryan.jobportal.employer.entities.Employer;
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
public class Job extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(length = 300)
    private String responsibilities;

    @Column(length = 300)
    private String requirements;

    @Column(length = 200)
    private String benefits;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkplaceType workplaceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    private Integer vacancies;

    private Integer minimumExperience;

    private Integer maximumExperience;

    private Double minimumSalary;

    private Double maximumSalary;

    private Boolean salaryNegotiable;

    private CurrencyType currency;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pinCode;

    private Double latitude;

    private Double longitude;

    private Boolean relocationAvailable;

    private Boolean accommodationProvided;

    private Boolean transportationProvided;

    private Boolean foodProvided;

    private Instant applicationDeadline;

    private Instant expiresAt;

    private Integer applicationCount;
}
