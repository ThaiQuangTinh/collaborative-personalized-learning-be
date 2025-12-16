package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostLikeService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<Object>> likePost(
            @PathVariable String postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        postLikeService.likePost(userDetails.getUserId(), postId);

        return ResponseUtil.created(null, "Post liked successfully!", httpRequest);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Object>> unlikePost(
            @PathVariable String postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        postLikeService.unlikePost(userDetails.getUserId(), postId);

        return ResponseUtil.success(null, "Post unliked successfully!", HttpStatus.OK, httpRequest);
    }

}

