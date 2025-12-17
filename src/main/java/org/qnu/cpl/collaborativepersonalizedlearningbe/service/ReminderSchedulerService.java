package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.time.LocalDateTime;

public interface ReminderSchedulerService {

    void scheduleLessonReminderViaEmail(
            String userId, String toEmail, String lessonTitle, String learningPathName, LocalDateTime remindAt
    );

    void scheduleLessonReminderViaPush(String userId, String lessonId, String pathId, LocalDateTime remindAt);

}
