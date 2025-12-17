package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateUserSettingRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserSettingResponse;

public interface UserSettingService {

    void initialUserSettings(String userId);

    UserSettingResponse getUserSettingsByUserId(String userId);

    UserSettingResponse setDefaultUserSettings(String userId);

    UserSettingResponse updateUserSettings(String userId, UpdateUserSettingRequest request);

    Integer getLessonReminderMinutes(String userId);

    boolean isEnabledEmailNotification(String userId);

    boolean isEnabledPushNotitication(String userId);

}
