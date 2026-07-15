package com.aryan.jobportal.auth.entities;

import com.aryan.jobportal.common.entities.Base;
import com.aryan.jobportal.common.enums.AppUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser extends Base implements UserDetails {

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isEmailVerified = false;

    private String contactNumber;

    private boolean isContactNumberVerified = false;

    private String profilePhoto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppUserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }
}
