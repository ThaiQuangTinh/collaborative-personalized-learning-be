package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LessonResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.TopicService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    // Create topic.
    @PostMapping
    public ResponseEntity<ApiResponse<TopicResponse>> createTopic(
            @RequestBody CreateTopicRequest request,
            HttpServletRequest httpRequest) {

        TopicResponse response = topicService.createTopic(request);

        return ResponseUtil.created(response, "Topic created successfully!", httpRequest);
    }

    // Update topic.
    @PutMapping("/{topicId}")
    public ResponseEntity<ApiResponse<TopicResponse>> updateTopic(
            @PathVariable String topicId,
            @RequestBody UpdateTopicRequest request,
            HttpServletRequest httpRequest) {

        TopicResponse response = topicService.updateTopic(topicId, request);

        return ResponseUtil.success(response, "Topic updated successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Delete topic.
    @DeleteMapping("/{topicId}")
    public ResponseEntity<ApiResponse<Object>> deleteTopic(@PathVariable String topicId,
                                                                  HttpServletRequest httpRequest) {

        topicService.deleteTopic(topicId);

        return ResponseUtil.success(null, "Topic deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Get all note by topic id
    @GetMapping("/{topicId}/notes")
    public ResponseEntity<ApiResponse<List<NoteResponse>>> getNotesByTopicId(
            @PathVariable String topicId,
            HttpServletRequest httpRequest
    ) {

        List<NoteResponse> noteList = topicService.getNotesByTopicId(topicId);

        return ResponseUtil.success(noteList, "Get notes successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Get all lesson by topic id.
    @GetMapping("/{topicId}/lessons")
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getLessonsByTopicId(
            @PathVariable String topicId,
            HttpServletRequest httpRequest) {

        List<LessonResponse> lessonList = topicService.getLessonsByTopicId(topicId);

        return ResponseUtil.success(lessonList, "Get lessons successfully!",
                HttpStatus.OK, httpRequest);
    }

}
