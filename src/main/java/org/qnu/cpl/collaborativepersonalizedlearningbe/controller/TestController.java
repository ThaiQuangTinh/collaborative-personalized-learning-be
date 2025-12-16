package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ScheduleRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

//    private final ThreadPoolTaskScheduler scheduler;
//
//    private final MailService mailService;
//
//    @PostMapping("/schedule")
//    public ResponseEntity<?> scheduleAction(@RequestBody ScheduleRequest request) {
//        LocalDateTime dateTime = request.getRunAt();
//
//        Date runDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
//
//        ScheduledFuture<?> future = scheduler.schedule(() -> {
//            // 🧩 Hành động sẽ chạy đúng vào thời điểm này
//            System.out.println("🎯 Hành động được chạy tại: " + LocalDateTime.now());
//            System.out.println("🪄 Nội dung hành động: " + request.getMessage());
//            // 👉 Ở đây bạn có thể gọi service, gửi email, update DB, v.v.
//            mailService.sendHtmlMail(
//                    "lamthovpn@gmail.com",
//                    "Nhắc nhở học bài",
//                    "<h2>Làm ơn hãy học bài đi!</h2>"
//            );
//        }, runDate);
//
//        return ResponseEntity.ok("✅ Đã lên lịch thành công cho " + dateTime);
//    }

}
