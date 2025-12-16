package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NotificationResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UnreadNotificationCountResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NotificationService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Object>> deleteNotification(
            @PathVariable String notificationId,
            HttpServletRequest httpRequest) {

        notificationService.deleteNotificationById(notificationId);

        return ResponseUtil.success(null, "Notification deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Object>> markAsReadNotification(
            @PathVariable String notificationId,
            HttpServletRequest httpRequest) {

        notificationService.markAsReadNotificationById(notificationId);

        return ResponseUtil.success(null, "Notification mark as read successfully!",
                HttpStatus.OK, httpRequest);
    }

    @PatchMapping("/read")
    public ResponseEntity<ApiResponse<Object>> markAsAllReadNotification(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        notificationService.markAsAllReadNotificationByUserId(userDetails.getUserId());

        return ResponseUtil.success(null, "Notification mark as all read successfully!",
                HttpStatus.OK, httpRequest);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<UnreadNotificationCountResponse>> getUnreadCountNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        UnreadNotificationCountResponse response =
                notificationService.getUnreadCountNotification(userDetails.getUserId());

        return ResponseUtil.success(response, "Get unread count read successfully!",
                HttpStatus.OK, httpRequest);
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getLatestNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        List<NotificationResponse> notifications =
                notificationService.getLatestNotificationsByUserId(userDetails.getUserId());

        return ResponseUtil.success(notifications, "Get latest notifications successfully",
                HttpStatus.OK, httpRequest
        );
    }

    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<Object>> deleteAllNotification(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        System.out.println("Role is: "  + userDetails.getRole());

        notificationService.deleteAllNotificationByUserId(userDetails.getUserId());

        return ResponseUtil.success(null, "Delete all notifications successfully",
                HttpStatus.OK, httpRequest
        );
    }
}
