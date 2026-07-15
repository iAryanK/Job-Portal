package com.aryan.jobportal.employer.entities;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.entities.Base;
import com.aryan.jobportal.common.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends Base {

    @Column(nullable = false)
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

    @Column(length = 100)
    private String tagline;

    @Column(length = 500)
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

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}
