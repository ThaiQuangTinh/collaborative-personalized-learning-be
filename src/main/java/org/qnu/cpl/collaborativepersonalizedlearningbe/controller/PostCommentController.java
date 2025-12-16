package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreatePostCommentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdatePostCommentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.PostCommentResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostCommentService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping()
    public ResponseEntity<ApiResponse<PostCommentResponse>> createComment(
            @RequestBody CreatePostCommentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest
    ) {
        PostCommentResponse response =
                postCommentService.createPostComment(userDetails.getUserId(), request);

        return ResponseUtil.created(response, "Comment created successfully!", httpRequest);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<PostCommentResponse>> updateComment(
            @PathVariable String commentId,
            @RequestBody UpdatePostCommentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest
    ) {
        PostCommentResponse response =
                postCommentService.updatePostComment(userDetails.getUserId(), commentId, request);

        return ResponseUtil.success(response, "Comment updated successfully!", HttpStatus.OK, httpRequest);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Object>> deleteComment(
            @PathVariable String commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest
    ) {
        postCommentService.deletePostCommentById(userDetails.getUserId(), commentId);

        return ResponseUtil.success(null, "Comment deleted successfully!", HttpStatus.OK, httpRequest);
    }

}
