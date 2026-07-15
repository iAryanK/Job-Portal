package com.aryan.jobportal.employer.dto.response;

import com.aryan.jobportal.common.enums.VerificationStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployerResp {

    private UUID id;

    private String companyName;

    @Column(unique = true)
    private String gstNumber;

    @Column(unique = true)
    private String cinNumber;

    private String industry;

    private String companySize;

    private Integer establishedYear;

    private String website;

    private String email;

    private String phone;

    private String tagline;

    private String description;

    private String logoUrl;

    private String coverImageUrl;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pinCode;

    private Double latitude;

    private Double longitude;

    private Boolean verified = false;

    private VerificationStatus verificationStatus;
}
