package com.aryan.jobportal.auth.dto.request;

import com.aryan.jobportal.common.enums.AppUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoogleAuthReq {

    @NotNull(message = "Invalid role")
    private AppUserRole role;
}
