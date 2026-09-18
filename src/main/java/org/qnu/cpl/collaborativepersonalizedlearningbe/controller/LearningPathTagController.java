package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.DeleteLearningPathTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateLearningPathTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LearningPathTagService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/learning-path-tags")
@RequiredArgsConstructor
public class LearningPathTagController {

    private final LearningPathTagService learningPathTagService;

    // Create learning path tag.
    @PostMapping
    public ResponseEntity<ApiResponse<List<CreateLearningPathTagResponse>>> createLearningPathTag(
            @RequestBody List<CreateLearningPathTagRequest> requests,
            HttpServletRequest httpRequest) {

        List<CreateLearningPathTagResponse> response = learningPathTagService.createLearningPathTag(requests);

        return ResponseUtil.created(response, "Learning Path Tag created successfully!", httpRequest);
    }

    // Delete learning path tag.
    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> deleteLearningPathTag(
            @RequestBody DeleteLearningPathTagRequest request,
            HttpServletRequest httpRequest) {

        learningPathTagService.deleteLearningPathTag(request);

        return ResponseUtil.success(null, "Learning path tag deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

}
