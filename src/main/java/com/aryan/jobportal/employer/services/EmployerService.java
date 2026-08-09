package com.aryan.jobportal.employer.services;

import com.aryan.jobportal.employer.dto.request.CreateEmployerReq;
import com.aryan.jobportal.employer.dto.request.FindEmployersFilterReq;
import com.aryan.jobportal.employer.dto.request.PatchEmployerReq;
import com.aryan.jobportal.employer.dto.response.EmployerResp;
import com.aryan.jobportal.employer.dto.response.EmployerSummaryResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployerService {
    Page<EmployerSummaryResp> findEmployers(FindEmployersFilterReq filter, Pageable pageable);

    EmployerResp findEmployerById(UUID id);

    EmployerResp createEmployer(CreateEmployerReq createEmployerReq);

    EmployerResp patchEmployerById(UUID id, PatchEmployerReq patchEmployerReq);
}
