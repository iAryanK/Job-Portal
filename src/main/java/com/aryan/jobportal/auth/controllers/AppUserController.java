package com.aryan.jobportal.auth.controllers;

import com.aryan.jobportal.auth.dto.request.EmailAuthReq;
import com.aryan.jobportal.auth.dto.request.OtpReq;
import com.aryan.jobportal.auth.dto.response.AuthResp;
import com.aryan.jobportal.auth.services.AppUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/core")
public class AppUserController {

    private final AppUserService appUserService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/send-email-otp")
    public ResponseEntity<Void> sendEmailOTP(@Valid @RequestBody OtpReq otpReq) {
        appUserService.sendEmailOTP(otpReq);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate-via-email")
    public ResponseEntity<AuthResp> authenticateViaEmail(@Valid @RequestBody EmailAuthReq emailAuthReq, HttpServletResponse response) {
        AuthResp authResp = appUserService.authenticateViaEmail(emailAuthReq);
        addRefreshTokenCookie(response, authResp.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(authResp);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthResp> refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new AuthenticationServiceException("Refresh token not found inside the Cookies");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        AuthResp authResp = appUserService.refreshToken(refreshToken);
        addRefreshTokenCookie(response, authResp.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(authResp);
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
