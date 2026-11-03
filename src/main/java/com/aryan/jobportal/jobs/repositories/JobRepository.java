package com.aryan.jobportal.jobs.repositories;

import com.aryan.jobportal.jobs.entities.Job;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID>, JpaSpecificationExecutor<Job> {

    @EntityGraph(attributePaths = "employer")
    Optional<Job> findWithEmployerById(UUID id);
}
