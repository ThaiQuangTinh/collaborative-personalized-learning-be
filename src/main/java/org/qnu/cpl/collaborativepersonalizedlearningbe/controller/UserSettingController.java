package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateUserSettingRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserSettingResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserSettingService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-settings")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserSettingService userSettingService;

    //    GET /api/user-settings/me
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserSettingResponse>> getSettingByUserId(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        UserSettingResponse response = userSettingService.getUserSettingsByUserId(userDetails.getUserId());

        return ResponseUtil.created(response, "Get user settings successfully!", httpRequest);
    }

    //    POST /api/user-settings/default
    @PostMapping("/default")
    public ResponseEntity<ApiResponse<UserSettingResponse>> setDefaultSettingsForUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        UserSettingResponse response = userSettingService.setDefaultUserSettings(userDetails.getUserId());

        return ResponseUtil.created(response, "Get user settings successfully!", httpRequest);
    }

    //    PATCH /api/user-settings/me
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserSettingResponse>> updateUserSettingsByUserId(
            @RequestBody UpdateUserSettingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        System.out.println("Role is: " + userDetails.getRole().toString());

        UserSettingResponse response = userSettingService.updateUserSettings(userDetails.getUserId(), request);

        return ResponseUtil.created(response, "Get user settings successfully!", httpRequest);
    }

}
