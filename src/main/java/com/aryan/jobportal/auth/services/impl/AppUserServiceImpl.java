package com.aryan.jobportal.auth.services.impl;

import com.aryan.jobportal.auth.dto.request.EmailAuthReq;
import com.aryan.jobportal.auth.dto.request.OtpReq;
import com.aryan.jobportal.auth.dto.response.AuthResp;
import com.aryan.jobportal.auth.entities.AppUser;
import com.aryan.jobportal.auth.repositories.AppUserRepository;
import com.aryan.jobportal.auth.security.JwtService;
import com.aryan.jobportal.auth.services.AppUserService;
import com.aryan.jobportal.common.dto.MailReq;
import com.aryan.jobportal.common.services.MailService;
import com.aryan.jobportal.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.aryan.jobportal.auth.templates.MailTemplate.sendOtpTemplate;
import static com.aryan.jobportal.auth.utils.AuthUtil.generateOtp;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final MailService mailService;
    private final CacheManager cacheManager;
    private final String CACHE_NAME = "auth";

    @Override
    public void sendEmailOTP(OtpReq otpReq) {
        String otp = generateOtp(6);
        log.info("OTP generated: {}", otp);
        MailReq request = MailReq.builder()
                .to(otpReq.getEmail())
                .subject("Verify your email")
                .htmlContent(sendOtpTemplate(otp))
                .build();

        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;
        cache.put(otpReq.getEmail(), otp);

        // mailService.send(request);   // TODO: send otp on email
    }

    @Override
    public AuthResp authenticateViaEmail(EmailAuthReq emailAuthReq) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;

        String storedOtp = cache.get(emailAuthReq.getEmail(), String.class);
        if (storedOtp == null) {
            throw new RuntimeException("OTP expired or not found");
        }
        if (!storedOtp.equals(emailAuthReq.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        cache.evict(emailAuthReq.getEmail());

        AppUser user = appUserRepository.findByEmail(emailAuthReq.getEmail())
                .orElseGet(() -> {
                    AppUser newUser = new AppUser();
                    newUser.setEmail(emailAuthReq.getEmail());
                    newUser.setRole(emailAuthReq.getRole());
                    newUser.setName(null);
                    newUser.setProfilePhoto(null);

                    return appUserRepository.save(newUser);
                });
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResp(accessToken, refreshToken);
    }

    public AppUser getUserById(UUID userId) {
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")) ;
    }

    @Override
    public AuthResp refreshToken(String refreshToken) {
        UUID userId = jwtService.getUserIdFromToken(refreshToken);
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")) ;

        String accessToken = jwtService.generateAccessToken(appUser);
        String refreshedToken = jwtService.generateRefreshToken(appUser);
        log.info("access token: {}, refresh token: {}", accessToken, refreshedToken);
        return new AuthResp(accessToken, refreshedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User with email "+email+" is not found")) ;
    }
}
