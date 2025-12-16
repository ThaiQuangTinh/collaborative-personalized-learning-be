package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Notification;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NotificationResponse;

public class NotificationMapper {

    public static NotificationResponse toResponse(Notification notification) {
        if (notification == null) return null;

        NotificationResponse res = new NotificationResponse();
        res.setNotificationId(notification.getNotificationId());
        res.setSourceType(notification.getSourceType());
        res.setSourceId(notification.getSourceId());
        res.setMessage(notification.getMessage());
        res.setType(notification.getType());
        res.setMetadata(notification.getMetadata());
        res.setIsRead(notification.getIsRead());
        res.setSentAt(notification.getSentAt());
        res.setReadAt(notification.getReadAt());

        return res;
    }

}
