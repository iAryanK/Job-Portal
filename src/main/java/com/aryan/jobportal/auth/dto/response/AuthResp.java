package com.aryan.jobportal.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResp {

    private String accessToken;

    private String refreshToken;
}
