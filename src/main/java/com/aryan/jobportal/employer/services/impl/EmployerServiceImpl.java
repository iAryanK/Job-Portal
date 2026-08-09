package com.aryan.jobportal.employer.services.impl;

import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import com.aryan.jobportal.employer.dto.request.CreateEmployerReq;
import com.aryan.jobportal.employer.dto.request.FindEmployersFilterReq;
import com.aryan.jobportal.employer.dto.request.PatchEmployerReq;
import com.aryan.jobportal.employer.dto.response.EmployerResp;
import com.aryan.jobportal.employer.dto.response.EmployerSummaryResp;
import com.aryan.jobportal.employer.entities.Employer;
import com.aryan.jobportal.employer.mapper.UpdateEmployerMapper;
import com.aryan.jobportal.employer.repositories.EmployerRepository;
import com.aryan.jobportal.employer.services.EmployerService;
import com.aryan.jobportal.employer.specifications.EmployerSpecifications;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt",
            "updatedAt",
            "companyName",
            "industry",
            "establishedYear",
            "city",
            "state",
            "country"
    );

    private final EmployerRepository employerRepository;
    private final ModelMapper modelMapper;
    private final UpdateEmployerMapper updateEmployerMapper;

    @Override
    @Transactional
    public Page<EmployerSummaryResp> findEmployers(FindEmployersFilterReq filter, Pageable pageable) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.JOBSEEKER.equals(appUser.getRole())) {
            throw new AccessDeniedException("You are not authorized to browse employers");
        }

        FindEmployersFilterReq safeFilter = filter != null ? filter : new FindEmployersFilterReq();
        Pageable sanitizedPageable = sanitizePageable(pageable);
        Specification<Employer> specification = EmployerSpecifications.withFilter(safeFilter);

        return employerRepository.findAll(specification, sanitizedPageable)
                .map(employer -> modelMapper.map(employer, EmployerSummaryResp.class));
    }

    @Override
    public EmployerResp findEmployerById(UUID id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        return modelMapper.map(employer, EmployerResp.class);
    }

    @Override
    public EmployerResp createEmployer(CreateEmployerReq createEmployerReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!AppUserRole.EMPLOYER.equals(appUser.getRole()))
            throw new AccessDeniedException("Invalid request");
        if (employerRepository.existsById(appUser.getId()))
            throw new DuplicateRequestException("Profile already exists");

        Employer employer = modelMapper.map(createEmployerReq, Employer.class);

        boolean employerAlreadyExists = employerRepository.existsByEmail(createEmployerReq.getEmail());
        if (employerAlreadyExists)
            throw new DuplicateRequestException("User already exists");

        employer.setAppUser(appUser);
        employerRepository.save(employer);

        return modelMapper.map(employer, EmployerResp.class);
    }

    @Override
    @Transactional
    public EmployerResp patchEmployerById(UUID id, PatchEmployerReq patchEmployerReq) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        if (!employer.getAppUser().getId().equals(appUser.getId())) {
            throw new AccessDeniedException("You are not authorized to update this business");
        }

        updateEmployerMapper.update(patchEmployerReq, employer);

        employer = employerRepository.save(employer);

        return modelMapper.map(employer, EmployerResp.class);
    }

    private Pageable sanitizePageable(Pageable pageable) {
        int page = pageable != null ? pageable.getPageNumber() : 0;
        int size = pageable != null ? pageable.getPageSize() : 20;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (pageable != null && pageable.getSort().isSorted()) {
            List<Sort.Order> orders = pageable.getSort().stream()
                    .filter(order -> ALLOWED_SORT_FIELDS.contains(order.getProperty()))
                    .toList();
            if (!orders.isEmpty()) {
                sort = Sort.by(orders);
            }
        }

        return PageRequest.of(page, size, sort);
    }
}
