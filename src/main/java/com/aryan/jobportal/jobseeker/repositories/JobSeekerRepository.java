package com.aryan.jobportal.jobseeker.repositories;

import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, UUID>, JpaSpecificationExecutor<JobSeeker> {
    JobSeeker findByAppUser_Id(UUID id);
}
