package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLessonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateLessonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LessonResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceResponse;

import java.util.List;

public interface LessonService {

    LessonResponse createLesson(String userId, CreateLessonRequest request);

    LessonResponse updateLesson(String lessonId, UpdateLessonRequest request);

    void deleteLesson(String lessonId);

    List<NoteResponse> getNotesByLessonId(String lessonId);

    List<ResourceResponse> getAllResourcesByLessonId(String lessonId);

}
