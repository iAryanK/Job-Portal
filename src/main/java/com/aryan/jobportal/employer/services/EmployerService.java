package com.aryan.jobportal.employer.services;

import com.aryan.jobportal.employer.dto.request.CreateEmployerReq;
import com.aryan.jobportal.employer.dto.request.PatchEmployerReq;
import com.aryan.jobportal.employer.dto.response.EmployerResp;

import java.util.UUID;

public interface EmployerService {
    EmployerResp findEmployerById(UUID id);

    EmployerResp createEmployer(CreateEmployerReq createEmployerReq);

    EmployerResp patchEmployerById(UUID id, PatchEmployerReq patchEmployerReq);
}
