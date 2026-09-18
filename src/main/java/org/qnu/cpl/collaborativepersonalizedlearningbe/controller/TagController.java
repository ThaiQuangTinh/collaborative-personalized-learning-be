package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.TagService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // Create tag.
    @PostMapping
    public ResponseEntity<ApiResponse<CreateTagResponse>> createTag(
            @RequestBody CreateTagRequest request,
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUserId();

        CreateTagResponse response = tagService.createTag(userId, request);

        return ResponseUtil.created(response, "Tag created successfully!", httpRequest);
    }

    // Update tag
    @PutMapping("/{tagId}")
    public ResponseEntity<ApiResponse<UpdateTagResponse>> updateTag(
            @PathVariable String tagId,
            @RequestBody UpdateTagRequest request,
            HttpServletRequest httpRequest) {

        UpdateTagResponse response = tagService.updateTag(tagId, request);

        return ResponseUtil.success(response, "Tag updated successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Delete tag
    @DeleteMapping("/{tagId}")
    public ResponseEntity<ApiResponse<Object>> deleteTag(@PathVariable String tagId,
            HttpServletRequest httpRequest) {

        tagService.deleteTag(tagId);

        return ResponseUtil.success(null, "Tag deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Get all tag
    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTagsByUser(
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUserId();

        List<TagResponse> response = tagService.getAllTagsByUser(userId);

        return ResponseUtil.success(response, "Get all tags by user successfully!",
                HttpStatus.OK, httpRequest);
    }

}
