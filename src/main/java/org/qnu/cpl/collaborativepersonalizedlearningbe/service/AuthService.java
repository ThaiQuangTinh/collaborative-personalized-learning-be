package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.EmailVerificationRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ForgotPasswordRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.LoginRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.RegisterRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ResetPasswordRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.TokenRefreshRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.VerifyEmailCodeRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.VerifyResetPasswordCodeRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LoginResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.RegisterResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TokenRefreshResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.VerifyResetPasswordCodeResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    void createEmailVerification(EmailVerificationRequest request);

    void verifyEmail(VerifyEmailCodeRequest request);

    void createPasswordReset(ForgotPasswordRequest request);

    VerifyResetPasswordCodeResponse verifyResetPassword(VerifyResetPasswordCodeRequest request);

    void resetPassword(ResetPasswordRequest request);

    LoginResponse login(LoginRequest request);

    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

}
