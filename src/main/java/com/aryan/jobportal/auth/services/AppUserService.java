package com.aryan.jobportal.auth.services;

import com.aryan.jobportal.auth.dto.request.EmailAuthReq;
import com.aryan.jobportal.auth.dto.request.OtpReq;
import com.aryan.jobportal.auth.dto.request.UpdateAppUserReq;
import com.aryan.jobportal.auth.dto.response.AppUserResp;
import com.aryan.jobportal.auth.dto.response.AuthResp;
import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.common.enums.AppUserRole;
import jakarta.validation.Valid;

import java.util.UUID;

public interface AppUserService {

    void sendEmailOTP(@Valid OtpReq otpReq);

    AuthResp authenticateViaEmail(@Valid EmailAuthReq emailAuthReq);

    AuthResp authenticateViaGoogle(String email, String name, String profilePhoto);

    AppUser getUserById(UUID userId);

    AppUser findUserByEmail(String email);

    AuthResp refreshToken(String refreshToken);

    AppUserResp getMe();

    void updateAppUser(UpdateAppUserReq updateAppUserReq);
}
