package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LoginResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TokenRefreshResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.RegisterResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.VerifyResetPasswordCodeResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.AuthService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Sign-Up
    @PostMapping("/registrations")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody RegisterRequest request,
            HttpServletRequest httpRequest) {

        RegisterResponse response = authService.register(request);

        return ResponseUtil.created(response, "User registered successfully!", httpRequest);
    }

    // Send verification code via email
    @PostMapping("/email-verifications")
    public ResponseEntity<ApiResponse<Object>> createEmailVerification(
            @RequestBody EmailVerificationRequest request,
            HttpServletRequest httpRequest) {

        authService.createEmailVerification(request);

        return ResponseUtil.success(null, "Send code to verify email successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Verify email
    @PostMapping("/email-verifications/verify")
    public ResponseEntity<ApiResponse<Object>> verifyEmail(
            @RequestBody VerifyEmailCodeRequest request,
            HttpServletRequest httpRequest) {

        authService.verifyEmail(request);

        return ResponseUtil.success(null, "Verify email successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Send code to forgot password
    @PostMapping("/password-resets")
    public ResponseEntity<ApiResponse<Object>> createPasswordReset(
            @RequestBody ForgotPasswordRequest request,
            HttpServletRequest httpRequest) {

        authService.createPasswordReset(request);

        return ResponseUtil.success(null, "Send code to forgot password successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Verify verification code to reset password
    @PostMapping("/password-resets/verify")
    public ResponseEntity<ApiResponse<VerifyResetPasswordCodeResponse>> verifyResetCode(
            @RequestBody VerifyResetPasswordCodeRequest request,
            HttpServletRequest httpRequest) {

        VerifyResetPasswordCodeResponse response = authService.verifyResetPassword(request);

        return ResponseUtil.success(response, "Verify reset password successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Reset password
    @PatchMapping("/password-resets/reset")
    public ResponseEntity<ApiResponse<Object>> resetPassword(
            @RequestBody ResetPasswordRequest request,
            HttpServletRequest httpRequest) {

        authService.resetPassword(request);

        return ResponseUtil.success(null, "Reset password successfully",
                HttpStatus.OK, httpRequest);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        LoginResponse response = authService.login(request);

        return ResponseUtil.success(response, "Login successfully!", HttpStatus.OK, httpRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(
            @RequestBody TokenRefreshRequest request,
            HttpServletRequest httpRequest) {

        TokenRefreshResponse response = authService.refreshToken(request);

        return ResponseUtil.created(response, "Get refresh token successfully!", httpRequest);
    }

}

