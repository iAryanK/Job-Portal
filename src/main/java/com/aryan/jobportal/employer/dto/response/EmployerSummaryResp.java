package com.aryan.jobportal.employer.dto.response;

import com.aryan.jobportal.common.enums.VerificationStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployerSummaryResp {

    private UUID id;

    private String companyName;

    private String industry;

    private String companySize;

    private Integer establishedYear;

    private String website;

    private String email;

    private String phone;

    private String tagline;

    private String logoUrl;

    private String city;

    private String district;

    private String state;

    private String country;

    private Boolean verified;

    private VerificationStatus verificationStatus;
}
