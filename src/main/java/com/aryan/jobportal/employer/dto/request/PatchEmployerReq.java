package com.aryan.jobportal.employer.dto.request;

import lombok.Data;

@Data
public class PatchEmployerReq {

    private String companyName;

    private String gstNumber;

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
}
