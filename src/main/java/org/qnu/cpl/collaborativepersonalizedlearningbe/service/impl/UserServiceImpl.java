package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Notification;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.NotificationMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateUserProfileRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UploadAvatarRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UserChangePasswordRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AvatarUrlResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NotificationResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UploadAvatarResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserProfileResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.NotificationRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MinioService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${minio.buckets.avatars}")
    private String avatarBucket;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MinioService minioService;

    private final NotificationRepository notificationRepository;

    @Override
    public UserProfileResponse getUserInfo(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setAddress(user.getAddress());
        userProfileResponse.setAvatarUrl(this.getAvatarUrlByUserId(user.getUserId()).getAvatarUrl());
        userProfileResponse.setGender(user.getGender());
        userProfileResponse.setPhone(user.getPhone());
        userProfileResponse.setFullname(user.getFullname());
        userProfileResponse.setEmailVerified(user.getEmailVerified());
        userProfileResponse.setCreatedAt(user.getCreatedAt());

        return userProfileResponse;
    }

    @Override
    public UserProfileResponse updateUserInfo(String userId, UpdateUserProfileRequest request) {

        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        if (!"".equals(request.getPhone()) && !ValidationUtil.isValidPhone(request.getPhone())) {
            throw new AppException(ErrorCode.INVALID_PHONE_FORMAT);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isEmailChanged = !user.getEmail().equals(request.getEmail());

        if (isEmailChanged) {
            user.setEmail(request.getEmail());
            user.setEmailVerified(false);
        }

        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setGender("".equals(request.getGender().toString()) ? null : request.getGender());
        user.setFullname(request.getFullname());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new UserProfileResponse(
                user.getUserId(),
                user.getEmail(),
                user.getFullname(),
                this.getAvatarUrlByUserId(user.getUserId()).getAvatarUrl(),
                user.getPhone(),
                user.getAddress(),
                user.getGender(),
                user.getEmailVerified(),
                user.getCreatedAt()
        );
    }


    @Override
    public void changePassword(String userId, UserChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (request.getCurrentPassword().length() < 8) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }

        if (request.getNewPassword().length() < 8) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public UploadAvatarResponse uploadAvatar(String userId, UploadAvatarRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (user.getAvatarObject() != null) {
            minioService.deleteFile(avatarBucket, user.getAvatarObject());
        }

        String fileExt = FilenameUtils.getExtension(request.getFile().getOriginalFilename());
        String objectName = "avatars/" + userId + "/" + UUIDUtil.generate() + "." + fileExt;

        minioService.uploadFile(request.getFile(), avatarBucket, objectName);

        user.setAvatarObject(objectName);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return new UploadAvatarResponse(
                this.getAvatarUrlByUserId(userId).getAvatarUrl()
        );
    }

    @Override
    public AvatarUrlResponse getAvatarUrlByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String avatarUrl = minioService.getFileUrl(avatarBucket, user.getAvatarObject());

        return new AvatarUrlResponse(avatarUrl);
    }

    @Override
    public List<NotificationResponse> getAllNotificationsByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Notification> notifications = notificationRepository.findAllByUser_UserId(userId);

        return notifications.stream().map(NotificationMapper::toResponse).toList();
    }

}
