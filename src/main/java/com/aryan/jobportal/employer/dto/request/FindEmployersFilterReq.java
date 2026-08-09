package com.aryan.jobportal.employer.dto.request;

import com.aryan.jobportal.common.enums.VerificationStatus;
import lombok.Data;

@Data
public class FindEmployersFilterReq {

    private String search;

    private String industry;

    private String companySize;

    private String city;

    private String district;

    private String state;

    private String country;

    private Boolean verified;

    private VerificationStatus verificationStatus;

    private Integer minEstablishedYear;

    private Integer maxEstablishedYear;
}
