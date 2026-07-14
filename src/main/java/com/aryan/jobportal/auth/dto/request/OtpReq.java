package com.aryan.jobportal.auth.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class OtpReq {

    @Email
    private String email;
}
