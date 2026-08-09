package com.aryan.jobportal.jobseeker.specifications;

import com.aryan.jobportal.common.enums.Gender;
import com.aryan.jobportal.jobseeker.dto.request.FindJobSeekersFilterReq;
import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class JobSeekerSpecifications {

    private JobSeekerSpecifications() {
    }

    public static Specification<JobSeeker> withFilter(FindJobSeekersFilterReq filter) {
        List<Specification<JobSeeker>> specifications = new ArrayList<>();
        specifications.add(matchesSearch(filter.getSearch()));
        specifications.add(equalsEnum("gender", filter.getGender()));
        specifications.add(equalsIgnoreCase("city", filter.getCity()));
        specifications.add(equalsIgnoreCase("district", filter.getDistrict()));
        specifications.add(equalsIgnoreCase("state", filter.getState()));
        specifications.add(equalsIgnoreCase("country", filter.getCountry()));
        specifications.add(equalsBoolean("currentlyWorking", filter.getCurrentlyWorking()));
        specifications.add(equalsBoolean("willingToRelocate", filter.getWillingToRelocate()));
        specifications.add(equalsBoolean("openToRemote", filter.getOpenToRemote()));
        specifications.add(containsIgnoreCase("preferredJobLocation", filter.getPreferredJobLocation()));
        specifications.add(containsIgnoreCase("highestQualification", filter.getHighestQualification()));
        specifications.add(containsIgnoreCase("specialization", filter.getSpecialization()));
        specifications.add(minExperienceYears(filter.getMinExperienceYears()));
        specifications.add(maxExperienceYears(filter.getMaxExperienceYears()));
        return combine(specifications);
    }

    private static Specification<JobSeeker> combine(List<Specification<JobSeeker>> specifications) {
        Specification<JobSeeker> combined = null;
        for (Specification<JobSeeker> specification : specifications) {
            if (specification == null) {
                continue;
            }
            combined = combined == null ? specification : combined.and(specification);
        }
        return combined;
    }

    private static Specification<JobSeeker> matchesSearch(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }

        String pattern = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("name")), pattern),
                cb.like(cb.lower(root.get("headline")), pattern),
                cb.like(cb.lower(root.get("summary")), pattern),
                cb.like(cb.lower(root.get("specialization")), pattern),
                cb.like(cb.lower(root.get("highestQualification")), pattern),
                cb.like(cb.lower(root.get("currentCompany")), pattern),
                cb.like(cb.lower(root.get("currentDesignation")), pattern),
                cb.like(cb.lower(root.get("city")), pattern),
                cb.like(cb.lower(root.get("state")), pattern),
                cb.like(cb.lower(root.get("country")), pattern),
                cb.like(cb.lower(root.get("preferredJobLocation")), pattern)
        );
    }

    private static Specification<JobSeeker> containsIgnoreCase(String field, String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String pattern = "%" + value.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), pattern);
    }

    private static Specification<JobSeeker> equalsIgnoreCase(String field, String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(cb.lower(root.get(field)), value.trim().toLowerCase());
    }

    private static Specification<JobSeeker> equalsBoolean(String field, Boolean value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private static Specification<JobSeeker> equalsEnum(String field, Gender value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private static Specification<JobSeeker> minExperienceYears(Integer minExperienceYears) {
        if (minExperienceYears == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("experienceYears"), minExperienceYears);
    }

    private static Specification<JobSeeker> maxExperienceYears(Integer maxExperienceYears) {
        if (maxExperienceYears == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("experienceYears"), maxExperienceYears);
    }
}
