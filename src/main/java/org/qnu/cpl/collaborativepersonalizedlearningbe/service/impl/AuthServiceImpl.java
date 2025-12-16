package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.VerificationCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.EmailNotVerifiedException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.UserInfo;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LoginResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.RegisterResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TokenRefreshResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.VerifyResetPasswordCodeResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.VerificationCodeRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetailsService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.JwtTokenUtil;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.PasswordResetTokenUtil;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.AuthService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MailService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserSettingService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.CodeUtil;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ValidationUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    private final UserService userService;

    private final CustomUserDetailsService userDetailsService;

    private final UserSettingService userSettingService;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordResetTokenUtil passwordResetTokenUtil;


    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (request.getPassword().length() < 8) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }

        // Create new user.
        User user = new User();
        user.setUserId(UUIDUtil.generate());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFullname(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailVerified(false);
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        userSettingService.initialUserSettings(user.getUserId());

        return RegisterResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    public void createEmailVerification(EmailVerificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (user.getEmailVerified()) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_VERIFIED);
        }

        // Delete old code.
        verificationCodeRepository.deleteByUser(user);
        String code = CodeUtil.generateVerificationCode();

        System.out.println("Go here!");

        // Save code of user to DB.
        VerificationCode vCode = new VerificationCode();
        vCode.setUser(user);
        vCode.setCodeId(UUIDUtil.generate());
        vCode.setCode(code);
        vCode.setType(VerificationCode.VerificationCodeType.EMAIL_VERIFICATION);
        vCode.setCreatedAt(LocalDateTime.now());
        vCode.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        verificationCodeRepository.save(vCode);

        // Send code to email of user
        mailService.sendVerificationEmail(user.getEmail(), code);
    }

    @Override
    @Transactional
    public void verifyEmail(VerifyEmailCodeRequest request) {
        // Check userID and code.
        VerificationCode vCode = verificationCodeRepository.findByUserUserIdAndCode(
                request.getUserId(), request.getCode()
        ).orElseThrow(() -> new AppException(ErrorCode.INVALID_VERIFICATION_CODE));

        // Check expires time.
        if (vCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.VERIFICATION_CODE_EXPIRED);
        }

        // Set status of user.
        User user = vCode.getUser();
        user.setEmailVerified(true);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        // Delete code was used.
        verificationCodeRepository.save(vCode);
    }

    @Override
    @Transactional
    public void createPasswordReset(ForgotPasswordRequest request) {
        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Delete old code.
        verificationCodeRepository.deleteByUser(user);
        String code = CodeUtil.generateVerificationCode();

        // Save code of user to DB.
        VerificationCode vCode = new VerificationCode();
        vCode.setUser(user);
        vCode.setCodeId(UUIDUtil.generate());
        vCode.setCode(code);
        vCode.setType(VerificationCode.VerificationCodeType.PASSWORD_RESET);
        vCode.setCreatedAt(LocalDateTime.now());
        vCode.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        verificationCodeRepository.save(vCode);

        // Send code to email of user
        mailService.sendPasswordResetEmail(request.getEmail(), code);
    }

    @Override
    @Transactional
    public VerifyResetPasswordCodeResponse verifyResetPassword(
            VerifyResetPasswordCodeRequest request) {

        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Check userID and code.
        VerificationCode vCode = verificationCodeRepository.findByUserUserIdAndCode(
                user.getUserId(), request.getCode()
        ).orElseThrow(() -> new AppException(ErrorCode.INVALID_VERIFICATION_CODE));

        // Check expires time.
        if (vCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.VERIFICATION_CODE_EXPIRED);
        }

        String token = passwordResetTokenUtil.generateResetToken(user);

        // Delete code was used.
        verificationCodeRepository.delete(vCode);

        return new VerifyResetPasswordCodeResponse(token);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        passwordResetTokenUtil.validateResetToken(request.getToken());

        if (request.getNewPassword().length() < 8) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Find user via username or email.
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElse(null);

        // Check account.
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        // Check email validation
        if (!Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new EmailNotVerifiedException(user.getUserId(), user.getEmail());
        }

        // Create JWT token
        String accessToken = jwtTokenUtil.generateAccessToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        String avatarUrl = userService.getAvatarUrlByUserId(user.getUserId()).getAvatarUrl();

//        userSettingService.initialUserSettings(user.getUserId());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenUtil.getAccessTokenExpiry())
                .user(UserInfo.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .email(user.getEmail())
                        .fullName(user.getFullname())
                        .avatarUrl(avatarUrl == null ? "" : avatarUrl)
                        .isVerifiedEmail(user.getEmailVerified())
                        .build())
                .build();
    }


    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        jwtTokenUtil.validateToken(request.getRefreshToken());

        String username = jwtTokenUtil.getUsernameFromToken(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Tạo access token mới
        String newAccessToken = jwtTokenUtil.generateAccessToken((User) userDetails);

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType("Bearer")
                .expiresIn(jwtTokenUtil.getAccessTokenExpiry())
                .build();
    }

}
