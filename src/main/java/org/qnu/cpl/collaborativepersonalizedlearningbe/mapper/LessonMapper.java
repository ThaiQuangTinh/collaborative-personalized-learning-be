package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Lesson;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LessonResponse;

public class LessonMapper {

    public static LessonResponse toResponse(Lesson lesson) {
        if (lesson == null) return null;

        LessonResponse res = new LessonResponse();
        res.setLessonId(lesson.getLessonId());
        res.setTitle(lesson.getTitle());
        res.setStartTime(lesson.getStartTime());
        res.setEndTime(lesson.getEndTime());
        res.setStatus(lesson.getStatus());
        res.setDisplayIndex(lesson.getDisplayIndex());

        return res;
    }

}
