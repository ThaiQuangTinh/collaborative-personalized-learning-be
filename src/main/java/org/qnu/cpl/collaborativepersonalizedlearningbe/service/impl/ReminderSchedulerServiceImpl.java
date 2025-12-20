package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MailService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NotificationService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ReminderSchedulerService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserSettingService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ReminderSchedulerServiceImpl implements ReminderSchedulerService {

    private final TaskScheduler taskScheduler;

    private final NotificationService notificationService;

    private final MailService mailService;

    private final UserSettingService userSettingService;

    @Override
    public void scheduleLessonReminderViaEmail(
            String userId,
            String toEmail,
            String lessonTitle,
            String learningPathName,
            LocalDateTime remindAt
    ) {
        // Lấy số phút nhắc trước từ user setting
        Integer lessonReminderMinutes =
                userSettingService.getLessonReminderMinutes(userId);

        // Lấy NGÀY từ deadline
        LocalDate deadlineDate = remindAt.toLocalDate();

        // Mốc chuẩn: 23:59 của ngày đó
        LocalDateTime endOfDay =
                deadlineDate.atTime(23, 59);

        // Thời điểm gửi mail = 23:59 - X phút
        LocalDateTime scheduleTime =
                endOfDay.minusMinutes(lessonReminderMinutes);

        System.out.println("Test date time: " + scheduleTime);

        // Convert sang Instant
        Instant instant = scheduleTime
                .atZone(ZoneId.systemDefault())
                .toInstant();

        // Nếu thời điểm đã qua thì không schedule
        if (instant.isBefore(Instant.now())) {
            return;
        }

//        Instant instant = Instant.now().plusSeconds(20);

        // Schedule gửi mail
        taskScheduler.schedule(
                () -> mailService.sendLessonDeadlineReminder(
                        toEmail,
                        lessonTitle,
                        learningPathName,
                        remindAt
                ),
                instant
        );
    }

    @Override
    public void scheduleLessonReminderViaPush(String userId, String lessonId, String pathId, LocalDateTime remindAt) {
        // Lấy số phút nhắc trước từ user setting
        Integer lessonReminderMinutes =
                userSettingService.getLessonReminderMinutes(userId);

        // Lấy NGÀY từ deadline
        LocalDate deadlineDate = remindAt.toLocalDate();

        // Mốc chuẩn: 23:59 của ngày đó
        LocalDateTime endOfDay =
                deadlineDate.atTime(23, 59);

        // Thời điểm gửi mail = 23:59 - X phút
        LocalDateTime scheduleTime =
                endOfDay.minusMinutes(lessonReminderMinutes);

        System.out.println("Test date time: " + scheduleTime);

        // Convert sang Instant
        Instant instant = scheduleTime
                .atZone(ZoneId.systemDefault())
                .toInstant();

        // Nếu thời điểm đã qua thì không schedule
        if (instant.isBefore(Instant.now())) {
            return;
        }

//        Instant instant = Instant.now().plusSeconds(20);

        taskScheduler.schedule(
                () -> {
                    notificationService.sendLessonReminderNotification(userId, lessonId, pathId);
                },
                instant
        );
    }

}
