package com.aryan.jobportal.jobs.dto.request;

import com.aryan.jobportal.common.enums.*;
import com.aryan.jobportal.employer.entities.Employer;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateJobReq {

    private String title;

    private String description;

    private String responsibilities;

    private String requirements;

    private String benefits;

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

    private String pinCode;

    private Double latitude;

    private Double longitude;

    private Boolean relocationAvailable;

    private Boolean accommodationProvided;

    private Boolean transportationProvided;

    private Boolean foodProvided;

    private Instant applicationDeadline;

    private Instant expiresAt;
}
