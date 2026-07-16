package com.aryan.jobportal.auth.dto.response;

import com.aryan.jobportal.common.enums.AppUserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
public class AppUserResp {

    private UUID id;

    private String name;

    private String email;

    private boolean isEmailVerified;

    private String contactNumber;

    private boolean isContactNumberVerified;

    private String profilePhoto;

    private AppUserRole role;
}
