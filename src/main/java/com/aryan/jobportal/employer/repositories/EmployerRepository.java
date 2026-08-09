package com.aryan.jobportal.employer.repositories;

import com.aryan.jobportal.employer.entities.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EmployerRepository extends JpaRepository<Employer, UUID>, JpaSpecificationExecutor<Employer> {
    Employer findEmployerByAppUser_Id(UUID id);

    boolean existsByEmail(String email);
}
