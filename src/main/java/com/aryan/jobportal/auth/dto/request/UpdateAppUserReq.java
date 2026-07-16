package com.aryan.jobportal.auth.dto.request;

import com.aryan.jobportal.common.enums.AppUserRole;
import lombok.Data;

@Data
public class UpdateAppUserReq {

    private String name;

    private boolean isEmailVerified;

    private boolean isContactNumberVerified;

    private String profilePhoto;

    private AppUserRole role;
}
