package com.aryan.jobportal.jobs.dto.request;

import com.aryan.jobportal.common.enums.CurrencyType;
import com.aryan.jobportal.common.enums.EmploymentType;
import com.aryan.jobportal.common.enums.ExperienceLevel;
import com.aryan.jobportal.common.enums.WorkplaceType;
import lombok.Data;

import java.util.UUID;

@Data
public class FindJobsFilterReq {

    private String search;

    private EmploymentType employmentType;

    private WorkplaceType workplaceType;

    private ExperienceLevel experienceLevel;

    private CurrencyType currency;

    private String city;

    private String district;

    private String state;

    private String country;

    private Double minSalary;

    private Double maxSalary;

    private Integer minExperience;

    private Integer maxExperience;

    private Boolean salaryNegotiable;

    private Boolean relocationAvailable;

    private Boolean accommodationProvided;

    private Boolean transportationProvided;

    private Boolean foodProvided;

    private UUID employerId;

    private String industry;
}
