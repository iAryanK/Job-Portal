package com.aryan.jobportal.jobseeker.entities;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.entities.Base;
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
public class JobSeeker extends Base {

    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", unique = true, nullable = false)
    private AppUser appUser;

    @Column(nullable = false)
    private String name;

    private Instant dateOfBirth;

    private String gender;

    private String headline;

    @Column(length = 500)
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
