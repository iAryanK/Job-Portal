package com.aryan.jobportal.jobs.repositories;

import com.aryan.jobportal.jobs.entities.ApplicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationHistoryRepository extends JpaRepository<ApplicationHistory, UUID> {
}
