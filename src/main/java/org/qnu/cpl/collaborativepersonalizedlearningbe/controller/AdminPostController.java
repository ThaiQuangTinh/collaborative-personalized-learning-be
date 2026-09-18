package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminPostResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.AdminPostService;
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
@RequestMapping("/api/admin/posts")
@AllArgsConstructor
public class AdminPostController {

    private final AdminPostService adminPostService;

    // GET /api/admin/posts
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminPostResponse>>> getAllPosts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        List<AdminPostResponse> response = adminPostService.getAllPosts(userDetails.getUserId());

        return ResponseUtil.created(response, "Get all post successfully!", httpRequest);
    }

    // GET /api/admin/posts/{postId}
    @GetMapping("{postId}")
    public ResponseEntity<ApiResponse<AdminPostResponse>> getPostById(
            @PathVariable String postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        AdminPostResponse response = adminPostService.getPostById(userDetails.getUserId(), postId);

        return ResponseUtil.created(response, "Get post successfully!", httpRequest);
    }

    // DELETE /api/admin/posts/{postId}
    @DeleteMapping("{postId}")
    public ResponseEntity<ApiResponse<Object>> deletePostById(
            @PathVariable String postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        adminPostService.deletePostById(userDetails.getUserId(), postId);

        return ResponseUtil.created(null, "Get post successfully!", httpRequest);
    }

}
