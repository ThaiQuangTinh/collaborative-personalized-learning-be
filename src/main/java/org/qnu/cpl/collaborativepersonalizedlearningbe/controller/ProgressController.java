package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateProgressRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateProgressResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateProgressResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ProgressService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progresses")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    // Create progress.
    @PostMapping
    public ResponseEntity<ApiResponse<CreateProgressResponse>> createProgress(
            @RequestBody CreateProgressRequest request,
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUserId();

        CreateProgressResponse response = progressService.createProgress(userId, request);

        return ResponseUtil.created(response, "Progress created successfully!", httpRequest);
    }

    // Update progress.
    @PutMapping
    public ResponseEntity<ApiResponse<UpdateProgressResponse>> updateProgress(
            @RequestBody UpdateProgressRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        UpdateProgressResponse response = progressService.updateProgress(userDetails.getUserId(), request);

        return ResponseUtil.created(response, "Progress updated successfully!", httpRequest);
    }

}
