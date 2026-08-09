package com.aryan.jobportal.jobseeker.dto.request;

import com.aryan.jobportal.common.enums.Gender;
import lombok.Data;

@Data
public class FindJobSeekersFilterReq {

    private String search;

    private Gender gender;

    private String city;

    private String district;

    private String state;

    private String country;

    private Integer minExperienceYears;

    private Integer maxExperienceYears;

    private Boolean currentlyWorking;

    private Boolean willingToRelocate;

    private Boolean openToRemote;

    private String preferredJobLocation;

    private String highestQualification;

    private String specialization;
}
