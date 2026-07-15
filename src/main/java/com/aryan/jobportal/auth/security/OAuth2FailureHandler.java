package com.aryan.jobportal.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        request.getSession().removeAttribute("googleAuthReq");
        log.warn("Google OAuth failed: {}", exception.getMessage());

        String redirectUrl = UriComponentsBuilder
                .fromUriString(frontendUrl + "/auth/callback")
                .queryParam("error", "oauth_failed")
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
