package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminUserResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.AdminUserService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@AllArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // GET /api/admin/users
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminUserResponse>>> getAllUsers(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        List<AdminUserResponse> response = adminUserService.getAllUser(userDetails.getUserId());

        return ResponseUtil.created(response, "Get all user successfully!", httpRequest);
    }

    // GET /api/admin/users/{userId}
    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<AdminUserResponse>> getUserById(
            @PathVariable String userId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        AdminUserResponse response = adminUserService.getUserById(userDetails.getUserId(), userId);

        return ResponseUtil.created(response, "Get user successfully!", httpRequest);
    }

    // DELETE /api/admin/users/{userId}
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse<AdminUserResponse>> deletUserById(
            @PathVariable String userId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        adminUserService.deleteUserById(userDetails.getUserId(), userId);

        return ResponseUtil.created(null, "Delete user successfully!", httpRequest);
    }

}
