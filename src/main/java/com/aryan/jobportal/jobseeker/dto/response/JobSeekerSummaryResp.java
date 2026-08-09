package com.aryan.jobportal.jobseeker.dto.response;

import com.aryan.jobportal.common.enums.Gender;
import lombok.Data;

import java.util.UUID;

@Data
public class JobSeekerSummaryResp {

    private UUID id;

    private String name;

    private Gender gender;

    private String headline;

    private String summary;

    private String highestQualification;

    private String specialization;

    private Integer experienceYears;

    private Integer experienceMonths;

    private String currentCompany;

    private String currentDesignation;

    private String expectedCTC;

    private Boolean currentlyWorking;

    private Boolean willingToRelocate;

    private Boolean openToRemote;

    private String preferredJobLocation;

    private String city;

    private String state;

    private String country;

    private String resumeUrl;

    private String portfolioUrl;
}
