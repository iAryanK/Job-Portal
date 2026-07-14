package com.aryan.jobportal.auth.services;

import com.aryan.jobportal.auth.dto.request.EmailAuthReq;
import com.aryan.jobportal.auth.dto.request.OtpReq;
import com.aryan.jobportal.auth.dto.response.AuthResp;
import com.aryan.jobportal.auth.entities.AppUser;
import jakarta.validation.Valid;

import java.util.UUID;

public interface AppUserService {

    void sendEmailOTP(@Valid OtpReq otpReq);

    AuthResp authenticateViaEmail(@Valid EmailAuthReq emailAuthReq);

    AppUser getUserById(UUID userId);

    AuthResp refreshToken(String refreshToken);
}
