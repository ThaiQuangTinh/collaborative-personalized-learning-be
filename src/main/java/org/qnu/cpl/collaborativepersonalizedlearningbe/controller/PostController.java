package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreatePostRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdatePostRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.PostCommentResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.PostResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostCommentService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final PostCommentService postCommentService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        List<PostResponse> postResponseList = postService.getAllPost(userDetails.getUserId());

        return ResponseUtil.success(postResponseList, "Post created successfully!", HttpStatus.OK, httpRequest);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        PostResponse response = postService.createPost(userDetails.getUserId(), request);

        return ResponseUtil.created(response, "Post created successfully!", httpRequest);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable String postId,
            @RequestBody UpdatePostRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        PostResponse response = postService.updatePost(
                userDetails.getUserId(), postId, request
        );

        return ResponseUtil.success(response, "Post updated successfully!", HttpStatus.OK, httpRequest);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Object>> deletePostById(
            @PathVariable String postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        postService.deletePostById(userDetails.getUserId(), postId);

        return ResponseUtil.success(null, "Post deleted successfully!", HttpStatus.OK, httpRequest);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<PostCommentResponse>>> getCommentsByPostId(
            @PathVariable String postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest
    ) {
        List<PostCommentResponse> response =
                postCommentService.findAllCommentByPostId(userDetails.getUserId(), postId);

        return ResponseUtil.success(response, "Get comments successfully!", HttpStatus.OK, httpRequest);
    }

}
