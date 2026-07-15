package com.aryan.jobportal.jobseeker.dto.response;

import com.aryan.jobportal.common.enums.VerificationStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class JobSeekerResp {

    private String id;

    private String name;

    private Instant dateOfBirth;

    private String gender;

    private String headline;

    private String summary;

    private String highestQualification;

    private String specialization;

    private String instituteName;

    private Integer passingYear;

    private Double cgpa;

    private Integer experienceYears;

    private Integer experienceMonths;

    private String currentCompany;

    private String currentDesignation;

    private String currentCTC;

    private String expectedCTC;

    private Boolean currentlyWorking;

    private Boolean willingToRelocate;

    private Boolean openToRemote;

    private String preferredJobLocation;

    private String currentAddressLine1;

    private String currentAddressLine2;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pinCode;

    private String latitude;

    private String longitude;

    private String resumeUrl;

    private String portfolioUrl;
}
