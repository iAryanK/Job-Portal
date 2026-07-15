package com.aryan.jobportal.auth.security;

import com.aryan.jobportal.auth.dto.request.GoogleAuthReq;
import com.aryan.jobportal.auth.dto.response.AuthResp;
import com.aryan.jobportal.auth.services.AppUserService;
import com.aryan.jobportal.common.enums.AppUserRole;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AppUserService appUserService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${deploy.env}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        if (email == null || email.isBlank()) {
            throw new IllegalStateException("Google account did not provide an email address");
        }

        String name = oAuth2User.getAttribute("name");
        String profilePhoto = oAuth2User.getAttribute("picture");

        log.info("Google OAuth success for email={}", email);

        AuthResp authResp = appUserService.authenticateViaGoogle(email, name, profilePhoto);
        addRefreshTokenCookie(response, authResp.getRefreshToken());

        String redirectUrl = UriComponentsBuilder
                .fromUriString(frontendUrl + "/auth/callback")
                .queryParam("accessToken", authResp.getAccessToken())
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
