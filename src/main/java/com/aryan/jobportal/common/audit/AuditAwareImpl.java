package com.aryan.jobportal.common.audit;

import com.aryan.jobportal.auth.entities.AppUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuditAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof AppUser user) {
            return Optional.of(user.getId());
        }

        return Optional.empty();
    }
}
