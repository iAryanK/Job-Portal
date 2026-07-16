package com.aryan.jobportal.auth.dto.request;

import com.aryan.jobportal.common.enums.AppUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailAuthReq {

    @Email
    private String email;

    private String otp;
}
