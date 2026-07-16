package com.aryan.jobportal.jobs.repositories;

import com.aryan.jobportal.jobs.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByJob_Id(UUID jobId);

    List<Application> findByJobSeeker_Id(UUID jobSeekerId);

    boolean existsByJob_IdAndJobSeeker_Id(UUID id, UUID id1);
}
