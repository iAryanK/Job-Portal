package com.aryan.jobportal.employer.repositories;

import com.aryan.jobportal.employer.entities.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployerRepository extends JpaRepository<Employer, UUID> {
}
