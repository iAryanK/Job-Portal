package com.aryan.jobportal.jobs.dto.response;

import com.aryan.jobportal.common.enums.*;
import com.aryan.jobportal.employer.entities.Employer;

import java.time.Instant;
import java.util.UUID;

public class JobSummaryResp {

    private UUID id;

    private String title;

    private String requirements;

    private Employer employer;

    private EmploymentType employmentType;

    private WorkplaceType workplaceType;

    private ExperienceLevel experienceLevel;

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

    private Instant applicationDeadline;
}
