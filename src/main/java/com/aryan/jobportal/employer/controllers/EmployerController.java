package com.aryan.jobportal.employer.controllers;

import com.aryan.jobportal.employer.dto.request.CreateEmployerReq;
import com.aryan.jobportal.employer.dto.request.FindEmployersFilterReq;
import com.aryan.jobportal.employer.dto.request.PatchEmployerReq;
import com.aryan.jobportal.employer.dto.response.EmployerResp;
import com.aryan.jobportal.employer.dto.response.EmployerSummaryResp;
import com.aryan.jobportal.employer.services.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employer/core")
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping
    public ResponseEntity<Page<EmployerSummaryResp>> findEmployers(
            @ModelAttribute FindEmployersFilterReq filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EmployerSummaryResp> employers = employerService.findEmployers(filter, pageable);
        return ResponseEntity.ok(employers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResp> findEmployerById(@PathVariable UUID id) {
        EmployerResp employer = employerService.findEmployerById(id);
        return ResponseEntity.ok(employer);
    }

    @PostMapping
    public ResponseEntity<EmployerResp> createEmployer(@RequestBody CreateEmployerReq createEmployerReq) {
        EmployerResp employer = employerService.createEmployer(createEmployerReq);
        return ResponseEntity.ok(employer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployerResp> patchEmployerById(@PathVariable UUID id,
                                                          @RequestBody PatchEmployerReq patchEmployerReq) {
        EmployerResp employer = employerService.patchEmployerById(id, patchEmployerReq);
        return ResponseEntity.ok(employer);
    }
}
