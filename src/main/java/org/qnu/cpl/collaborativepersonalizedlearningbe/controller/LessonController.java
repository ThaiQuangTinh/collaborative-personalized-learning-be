package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLessonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateLessonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LessonService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // Create lesson.
    @PostMapping
    public ResponseEntity<ApiResponse<LessonResponse>> createLesson(
            @RequestBody CreateLessonRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpRequest) {

        LessonResponse response = lessonService.createLesson(userDetails.getUserId(), request);

        return ResponseUtil.created(response, "Lesson created successfully!", httpRequest);
    }

    // Update lesson.
    @PutMapping("/{lessonId}")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLesson(
            @PathVariable String lessonId,
            @RequestBody UpdateLessonRequest request,
            HttpServletRequest httpRequest) {

        LessonResponse response = lessonService.updateLesson(lessonId, request);

        return ResponseUtil.success(response, "Lesson updated successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Delete lesson.
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<ApiResponse<Object>> deleteLesson(@PathVariable String lessonId,
                                                            HttpServletRequest httpRequest) {

        lessonService.deleteLesson(lessonId);

        return ResponseUtil.success(null, "Lesson deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Get all notes by lesson id
    @GetMapping("/{lessonId}/notes")
    public ResponseEntity<ApiResponse<List<NoteResponse>>> getNotesByLessonId(
            @PathVariable String lessonId,
            HttpServletRequest httpRequest) {

        List<NoteResponse> noteList = lessonService.getNotesByLessonId(lessonId);

        return ResponseUtil.success(noteList, "Get notes successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Get all resources by lesson id.
    @GetMapping("{lessonId}/resources")
    public ResponseEntity<ApiResponse<List<ResourceResponse>>> getResourcesByLessonId(
            @PathVariable String lessonId,
            HttpServletRequest httpRequest) {

        List<ResourceResponse> responses = lessonService.getAllResourcesByLessonId(lessonId);

        return ResponseUtil.success(responses, "Get resources successfully!",
                HttpStatus.OK, httpRequest);
    }


}
