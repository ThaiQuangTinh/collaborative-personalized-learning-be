package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateUserProfileRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UploadAvatarRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UserChangePasswordRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LearningPathService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LearningPathService learningPathService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        String userId = userDetails.getUserId();

        UserProfileResponse response = userService.getUserInfo(userId);

        return ResponseUtil.success(response, "Get user profile successfully!",
                HttpStatus.OK, httpRequest);
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @RequestBody UpdateUserProfileRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        String userId = userDetails.getUserId();

        UserProfileResponse response = userService.updateUserInfo(userId, request);

        return ResponseUtil.success(response, "Update user profile successfully!",
                HttpStatus.OK, httpRequest);
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Object>> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UserChangePasswordRequest request,
            HttpServletRequest httpRequest) {

        String userId = userDetails.getUserId();

        userService.changePassword(userId, request);

        return ResponseUtil.success(null, "Change user password successfully!",
                HttpStatus.OK, httpRequest);
    }

    @PostMapping("/me/avatar")
    public ResponseEntity<ApiResponse<UploadAvatarResponse>> uploadAvatar(
            @ModelAttribute UploadAvatarRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        UploadAvatarResponse res = userService.uploadAvatar(userDetails.getUserId(), request);

        return ResponseUtil.success(res, "Upload avatar successfully!",
                HttpStatus.OK, httpRequest);
    }

    @GetMapping("/me/avatar")
    public ResponseEntity<ApiResponse<AvatarUrlResponse>> getAvatarUrl(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        AvatarUrlResponse response = userService.getAvatarUrlByUserId(userDetails.getUserId());

        return ResponseUtil.success(response, "Get avatar url successfully!",
                HttpStatus.OK, httpRequest);
    }

    @GetMapping("/me/notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        List<NotificationResponse> response = userService.getAllNotificationsByUser(userDetails.getUserId());

        return ResponseUtil.success(response, "Get all notifications successfully!",
                HttpStatus.OK, httpRequest);
    }

    @GetMapping("/me/statistics")
    public ResponseEntity<ApiResponse<List<LearningPathStatisticResponse>>> getAllPathStatistics(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        List<LearningPathStatisticResponse> response =
                learningPathService.getAllLearningPathStatistics(userDetails.getUserId());

        return ResponseUtil.success(response, "Get all learning path statistics successfully!",
                HttpStatus.OK, httpRequest);
    }

}
