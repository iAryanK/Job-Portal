package com.aryan.jobportal.jobs.specifications;

import com.aryan.jobportal.common.enums.JobStatus;
import com.aryan.jobportal.employer.entities.Employer;
import com.aryan.jobportal.jobs.dto.request.FindJobsFilterReq;
import com.aryan.jobportal.jobs.entities.Job;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class JobSpecifications {

    private JobSpecifications() {
    }

    public static Specification<Job> withFilter(FindJobsFilterReq filter) {
        List<Specification<Job>> specifications = new ArrayList<>();
        boolean isEmployerScoped = filter.getEmployerId() != null;

        if (!isEmployerScoped) {
            specifications.add(hasPublishedStatus());
            specifications.add(isActive());
        }

        specifications.add(matchesSearch(filter.getSearch()));
        specifications.add(equalsEnum("employmentType", filter.getEmploymentType()));
        specifications.add(equalsEnum("workplaceType", filter.getWorkplaceType()));
        specifications.add(equalsEnum("experienceLevel", filter.getExperienceLevel()));
        specifications.add(equalsEnum("currency", filter.getCurrency()));
        specifications.add(equalsIgnoreCase("city", filter.getCity()));
        specifications.add(equalsIgnoreCase("district", filter.getDistrict()));
        specifications.add(equalsIgnoreCase("state", filter.getState()));
        specifications.add(equalsIgnoreCase("country", filter.getCountry()));
        specifications.add(equalsBoolean("salaryNegotiable", filter.getSalaryNegotiable()));
        specifications.add(equalsBoolean("relocationAvailable", filter.getRelocationAvailable()));
        specifications.add(equalsBoolean("accommodationProvided", filter.getAccommodationProvided()));
        specifications.add(equalsBoolean("transportationProvided", filter.getTransportationProvided()));
        specifications.add(equalsBoolean("foodProvided", filter.getFoodProvided()));
        specifications.add(hasEmployerId(filter.getEmployerId()));
        specifications.add(hasIndustry(filter.getIndustry()));
        specifications.add(meetsMinSalary(filter.getMinSalary()));
        specifications.add(meetsMaxSalary(filter.getMaxSalary()));
        specifications.add(meetsMinExperience(filter.getMinExperience()));
        specifications.add(meetsMaxExperience(filter.getMaxExperience()));

        return combine(specifications);
    }

    private static Specification<Job> combine(List<Specification<Job>> specifications) {
        Specification<Job> combined = null;
        for (Specification<Job> specification : specifications) {
            if (specification == null) {
                continue;
            }
            combined = combined == null ? specification : combined.and(specification);
        }
        return combined;
    }

    private static Specification<Job> hasPublishedStatus() {
        return (root, query, cb) -> cb.equal(root.get("status"), JobStatus.PUBLISHED);
    }

    private static Specification<Job> isActive() {
        Instant now = Instant.now();
        return (root, query, cb) -> cb.and(
                cb.or(cb.isNull(root.get("expiresAt")), cb.greaterThan(root.get("expiresAt"), now)),
                cb.or(cb.isNull(root.get("applicationDeadline")), cb.greaterThan(root.get("applicationDeadline"), now))
        );
    }

    private static Specification<Job> matchesSearch(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }

        String pattern = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            Join<Job, Employer> employer = root.join("employer", JoinType.LEFT);
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern),
                    cb.like(cb.lower(root.get("requirements")), pattern),
                    cb.like(cb.lower(root.get("responsibilities")), pattern),
                    cb.like(cb.lower(root.get("benefits")), pattern),
                    cb.like(cb.lower(root.get("city")), pattern),
                    cb.like(cb.lower(root.get("state")), pattern),
                    cb.like(cb.lower(root.get("country")), pattern),
                    cb.like(cb.lower(employer.get("companyName")), pattern),
                    cb.like(cb.lower(employer.get("industry")), pattern)
            );
        };
    }

    private static <E extends Enum<E>> Specification<Job> equalsEnum(String field, E value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private static Specification<Job> equalsIgnoreCase(String field, String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(cb.lower(root.get(field)), value.trim().toLowerCase());
    }

    private static Specification<Job> equalsBoolean(String field, Boolean value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private static Specification<Job> hasEmployerId(java.util.UUID employerId) {
        if (employerId == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("employer").get("id"), employerId);
    }

    private static Specification<Job> hasIndustry(String industry) {
        if (!StringUtils.hasText(industry)) {
            return null;
        }
        String pattern = "%" + industry.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            Join<Job, Employer> employer = root.join("employer", JoinType.INNER);
            return cb.like(cb.lower(employer.get("industry")), pattern);
        };
    }

    private static Specification<Job> meetsMinSalary(Double minSalary) {
        if (minSalary == null) {
            return null;
        }
        return (root, query, cb) -> cb.or(
                cb.isNull(root.get("maximumSalary")),
                cb.greaterThanOrEqualTo(root.get("maximumSalary"), minSalary),
                cb.isTrue(root.get("salaryNegotiable"))
        );
    }

    private static Specification<Job> meetsMaxSalary(Double maxSalary) {
        if (maxSalary == null) {
            return null;
        }
        return (root, query, cb) -> cb.or(
                cb.isNull(root.get("minimumSalary")),
                cb.lessThanOrEqualTo(root.get("minimumSalary"), maxSalary),
                cb.isTrue(root.get("salaryNegotiable"))
        );
    }

    private static Specification<Job> meetsMinExperience(Integer minExperience) {
        if (minExperience == null) {
            return null;
        }
        return (root, query, cb) -> cb.or(
                cb.isNull(root.get("maximumExperience")),
                cb.greaterThanOrEqualTo(root.get("maximumExperience"), minExperience)
        );
    }

    private static Specification<Job> meetsMaxExperience(Integer maxExperience) {
        if (maxExperience == null) {
            return null;
        }
        return (root, query, cb) -> cb.or(
                cb.isNull(root.get("minimumExperience")),
                cb.lessThanOrEqualTo(root.get("minimumExperience"), maxExperience)
        );
    }
}
