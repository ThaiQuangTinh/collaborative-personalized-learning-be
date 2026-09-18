package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateShareLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.DeleteLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ExportLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ImportLearningPathJsonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateProgressPercentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateShareLearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathExportResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathStatisticResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ShareLearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateLearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LearningPathService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/learning-paths")
@RequiredArgsConstructor
public class LearningPathController {

        private final LearningPathService learningPathService;

        // Create tag.
        @PostMapping
        public ResponseEntity<ApiResponse<LearningPathResponse>> createLearningPath(
                        @RequestBody CreateLearningPathRequest request,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                LearningPathResponse response = learningPathService.createLearningPath(userId, request);

                return ResponseUtil.created(response, "Learning Path created successfully!", httpRequest);
        }

        // Update learning path.
        @PutMapping("/{pathId}")
        public ResponseEntity<ApiResponse<UpdateLearningPathResponse>> updateLearningPath(
                        @PathVariable String pathId,
                        @RequestBody UpdateLearningPathRequest request,
                        HttpServletRequest httpRequest) {

                UpdateLearningPathResponse response = learningPathService.updateLearningPath(pathId, request);

                return ResponseUtil.success(response, "Learning path updated successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Delete learning path.
        @DeleteMapping()
        public ResponseEntity<ApiResponse<Object>> deleteLearningPath(
                        @RequestBody DeleteLearningPathRequest request,
                        HttpServletRequest httpRequest) {

                learningPathService.deleteLearningPaths(request);

                return ResponseUtil.success(null, "Learning path deleted successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @GetMapping("/{pathId}")
        public ResponseEntity<ApiResponse<LearningPathResponse>> getLearningPathByPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest) {

                LearningPathResponse learningPathResponse = learningPathService.getLearningPathByPathId(pathId);

                return ResponseUtil.success(learningPathResponse, "Get all learning paths successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @PostMapping("/{pathId}/favorite")
        public ResponseEntity<ApiResponse<Object>> addFavoriteByPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                learningPathService.addFavoriteByPathId(userId, pathId);

                return ResponseUtil.success(null, "Add favorite learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @DeleteMapping("/{pathId}/favorite")
        public ResponseEntity<ApiResponse<Object>> deleteFavoriteByPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                learningPathService.deleteFavoriteByPathId(userId, pathId);

                return ResponseUtil.success(null, "Delete favorite learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @PostMapping("/{pathId}/archive")
        public ResponseEntity<ApiResponse<Object>> addArchiveByPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                learningPathService.addArchiveByPathId(userId, pathId);

                return ResponseUtil.success(null, "Add archive learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @DeleteMapping("/{pathId}/archive")
        public ResponseEntity<ApiResponse<Object>> deleteArchiveByPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                learningPathService.deleteArchiveByPathId(userId, pathId);

                return ResponseUtil.success(null, "Delete archive learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Get all learning path.
        @GetMapping
        public ResponseEntity<ApiResponse<List<LearningPathResponse>>> getAllLearningPathByUser(
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                List<LearningPathResponse> learningPathResponses = learningPathService
                                .getAllLearningPathsByUser(userId);

                return ResponseUtil.success(learningPathResponses, "Get all learning paths successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @GetMapping("/favorites")
        public ResponseEntity<ApiResponse<List<LearningPathResponse>>> getFavoriteLearningPaths(
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                List<LearningPathResponse> learningPathResponseList = learningPathService
                                .getFavoriteLearningPaths(userId);

                return ResponseUtil.success(learningPathResponseList, "Get favorite learning paths successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @GetMapping("/archives")
        public ResponseEntity<ApiResponse<List<LearningPathResponse>>> getArchivedLearningPaths(
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                List<LearningPathResponse> learningPathResponseList = learningPathService
                                .getArchivedLearningPaths(userId);

                return ResponseUtil.success(learningPathResponseList, "Get archive learning paths successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Get topics by learning path id.
        @GetMapping("/{pathId}/topics")
        public ResponseEntity<ApiResponse<List<TopicResponse>>> getTopicsByPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest) {

                List<TopicResponse> topicList = learningPathService.getTopicsByPathId(pathId);

                return ResponseUtil.success(topicList, "Get topic list successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Get tags by learning path id.
        @GetMapping("/{pathId}/tags")
        public ResponseEntity<ApiResponse<List<TagResponse>>> getTagsByLearningPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest) {

                List<TagResponse> tagList = learningPathService.getTagsByLearningPathId(pathId);

                return ResponseUtil.success(tagList, "Get tag list successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Get notes by learning path id.
        @GetMapping("/{pathId}/notes")
        public ResponseEntity<ApiResponse<List<NoteResponse>>> getNotesByLearningPathId(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest) {

                List<NoteResponse> noteList = learningPathService.getNotesByLearningPathId(pathId);

                return ResponseUtil.success(noteList, "Get note list successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Share learning path
        @PostMapping("/{pathId}/share")
        public ResponseEntity<ApiResponse<CreateShareLearningPathResponse>> shareLearningPath(
                        @PathVariable String pathId,
                        @RequestBody CreateShareLearningPathRequest request,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                CreateShareLearningPathResponse response = learningPathService.shareLearningPath(userId, pathId,
                                request);

                return ResponseUtil.success(response, "Share learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // Get learning path by share
        @GetMapping("/share/{token}")
        public ResponseEntity<ApiResponse<ShareLearningPathResponse>> getLearningPathByShare(
                        @PathVariable String token,
                        HttpServletRequest httpRequest) {

                ShareLearningPathResponse response = learningPathService.getLearningPathByShare(token);

                return ResponseUtil.success(response, "Get share learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @PostMapping("clone-from-share/{token}")
        public ResponseEntity<ApiResponse<LearningPathResponse>> cloneLearningPathFromShare(
                        @PathVariable String token,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                String userId = userDetails.getUserId();

                LearningPathResponse response = learningPathService.cloneLearningPathForUser(userId, token);

                return ResponseUtil.success(response, "Clone learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        @PostMapping("/export")
        public ResponseEntity<InputStreamResource> exportLearningPath(
                        @RequestBody ExportLearningPathRequest request) throws IOException {

                List<LearningPathExportResponse> data = learningPathService.exportLearningPaths(request);

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                                jsonString.getBytes(StandardCharsets.UTF_8));

                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                                "attachment; filename=learning_path_export.json")
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(new InputStreamResource(byteArrayInputStream));
        }

        @PostMapping("/import")
        public ResponseEntity<ApiResponse<List<LearningPathResponse>>> importLearningPathsFromFile(
                        @ModelAttribute ImportLearningPathJsonRequest request,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                List<LearningPathResponse> responses = learningPathService.importJsonLearningPath(
                                userDetails.getUserId(), request);

                return ResponseUtil.success(responses, "Export learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // PATCH /api/learning-paths/{pathId}/progress
        @PatchMapping("/{pathId}/progress")
        public ResponseEntity<ApiResponse<Object>> updateProgressPercent(
                        @PathVariable String pathId,
                        @RequestBody UpdateProgressPercentRequest request,
                        HttpServletRequest httpRequest) {

                learningPathService.updateProgressPercent(pathId, request);

                return ResponseUtil.success(null, "Update progress learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

        // statistics
        @GetMapping("/{pathId}/statistics")
        public ResponseEntity<ApiResponse<LearningPathStatisticResponse>> getLearningPathStatistics(
                        @PathVariable String pathId,
                        HttpServletRequest httpRequest,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

                LearningPathStatisticResponse responses = learningPathService
                                .getLearningPathStatistics(userDetails.getUserId(), pathId);

                return ResponseUtil.success(responses, "Export learning path successfully!",
                                HttpStatus.OK, httpRequest);
        }

}
