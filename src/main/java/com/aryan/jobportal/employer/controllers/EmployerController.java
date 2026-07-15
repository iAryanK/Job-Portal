package com.aryan.jobportal.employer.controllers;

import com.aryan.jobportal.employer.dto.request.CreateEmployerReq;
import com.aryan.jobportal.employer.dto.request.PatchEmployerReq;
import com.aryan.jobportal.employer.dto.response.EmployerResp;
import com.aryan.jobportal.employer.services.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employer/core")
public class EmployerController {

    private final EmployerService employerService;

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
