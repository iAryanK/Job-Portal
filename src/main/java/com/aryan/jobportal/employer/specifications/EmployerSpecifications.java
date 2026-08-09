package com.aryan.jobportal.employer.specifications;

import com.aryan.jobportal.common.enums.VerificationStatus;
import com.aryan.jobportal.employer.dto.request.FindEmployersFilterReq;
import com.aryan.jobportal.employer.entities.Employer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class EmployerSpecifications {

    private EmployerSpecifications() {
    }

    public static Specification<Employer> withFilter(FindEmployersFilterReq filter) {
        return Specification.allOf(
                matchesSearch(filter.getSearch()),
                containsIgnoreCase("industry", filter.getIndustry()),
                equalsIgnoreCase("companySize", filter.getCompanySize()),
                equalsIgnoreCase("city", filter.getCity()),
                equalsIgnoreCase("district", filter.getDistrict()),
                equalsIgnoreCase("state", filter.getState()),
                equalsIgnoreCase("country", filter.getCountry()),
                equalsBoolean("verified", filter.getVerified()),
                equalsEnum("verificationStatus", filter.getVerificationStatus()),
                minEstablishedYear(filter.getMinEstablishedYear()),
                maxEstablishedYear(filter.getMaxEstablishedYear())
        );
    }

    private static Specification<Employer> matchesSearch(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }

        String pattern = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("companyName")), pattern),
                cb.like(cb.lower(root.get("industry")), pattern),
                cb.like(cb.lower(root.get("tagline")), pattern),
                cb.like(cb.lower(root.get("description")), pattern),
                cb.like(cb.lower(root.get("city")), pattern),
                cb.like(cb.lower(root.get("state")), pattern),
                cb.like(cb.lower(root.get("country")), pattern)
        );
    }

    private static Specification<Employer> containsIgnoreCase(String field, String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String pattern = "%" + value.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), pattern);
    }

    private static Specification<Employer> equalsIgnoreCase(String field, String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(cb.lower(root.get(field)), value.trim().toLowerCase());
    }

    private static Specification<Employer> equalsBoolean(String field, Boolean value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private static Specification<Employer> equalsEnum(String field, VerificationStatus value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private static Specification<Employer> minEstablishedYear(Integer minEstablishedYear) {
        if (minEstablishedYear == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("establishedYear"), minEstablishedYear);
    }

    private static Specification<Employer> maxEstablishedYear(Integer maxEstablishedYear) {
        if (maxEstablishedYear == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("establishedYear"), maxEstablishedYear);
    }
}
