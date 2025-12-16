package org.qnu.cpl.collaborativepersonalizedlearningbe.scheduler;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationScheduler {

//    private final SimpMessagingTemplate messagingTemplate;
//
//    public NotificationScheduler(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

//    @Scheduled(fixedRate = 1800000) // 5 phút
//    public void sendDemoNotification() {
//        Map<String, String> noti = new HashMap<>();
//        noti.put("message", "Đây là notification demo " + LocalDateTime.now());
//        messagingTemplate.convertAndSend("/topic/notifications", noti);
//    }

}
