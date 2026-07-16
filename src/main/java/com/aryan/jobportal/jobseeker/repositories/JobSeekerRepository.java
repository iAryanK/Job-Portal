package com.aryan.jobportal.jobseeker.repositories;

import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, UUID> {
    JobSeeker findByAppUser_Id(UUID id);
}
