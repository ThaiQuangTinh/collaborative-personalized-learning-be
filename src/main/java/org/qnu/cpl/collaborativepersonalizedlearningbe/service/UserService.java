package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateUserProfileRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UploadAvatarRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UserChangePasswordRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AvatarUrlResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NotificationResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UploadAvatarResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserProfileResponse;

import java.util.List;

public interface UserService {

    UserProfileResponse getUserInfo(String userId);

    UserProfileResponse updateUserInfo(String userId, UpdateUserProfileRequest request);

    void changePassword(String userId, UserChangePasswordRequest request);

    UploadAvatarResponse uploadAvatar(String userId, UploadAvatarRequest request);

    AvatarUrlResponse getAvatarUrlByUserId(String userId);

    List<NotificationResponse> getAllNotificationsByUser(String userId);

}
