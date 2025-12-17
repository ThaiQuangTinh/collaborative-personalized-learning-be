package org.qnu.cpl.collaborativepersonalizedlearningbe.service;


import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MailService {

    void sendHtmlMail(String to, String subject, String htmlContent);

    void sendVerificationEmail(String to, String code);

    void sendPasswordResetEmail(String to, String code);

    void sendLessonDeadlineReminder(
            String to, String lessonTitle,
            String learningPathName, LocalDateTime deadline
    );

}
