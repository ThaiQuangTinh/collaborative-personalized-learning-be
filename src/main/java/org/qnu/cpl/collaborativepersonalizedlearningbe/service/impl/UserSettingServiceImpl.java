package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.UserSettings;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Language;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Theme;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateUserSettingRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserSettingResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserSettingRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserSettingService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingService {

    private final UserSettingRepository userSettingRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void initialUserSettings(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UserSettings userSettings = new UserSettings();
        userSettings.setSettingId(UUIDUtil.generate());
        userSettings.setUser(user);
        userSettings.setLanguage(Language.VI);
        userSettings.setTheme(Theme.LIGHT);
        userSettings.setNotificationEnabled(false);
        userSettings.setLessonReminderMinutes(60);
        userSettings.setEmailNotificationEnabled(false);
        userSettings.setPushNotificationEnabled(false);
        userSettings.setCreatedAt(LocalDateTime.now());
        userSettings.setUpdatedAt(LocalDateTime.now());

        userSettingRepository.save(userSettings);
    }

    @Override
    public UserSettingResponse getUserSettingsByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        UserSettings userSettings = userSettingRepository.findAllByUser_UserId(userId);

        return new UserSettingResponse(
                userSettings.getTheme(),
                userSettings.getLanguage(),
                userSettings.isNotificationEnabled(),
                userSettings.getLessonReminderMinutes(),
                userSettings.isEmailNotificationEnabled(),
                userSettings.isPushNotificationEnabled()
        );
    }

    @Override
    @Transactional
    public UserSettingResponse setDefaultUserSettings(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        UserSettings userSettings = userSettingRepository.findAllByUser_UserId(userId);

        userSettings.setLanguage(Language.VI);
        userSettings.setTheme(Theme.LIGHT);
        userSettings.setNotificationEnabled(false);
        userSettings.setLessonReminderMinutes(60);
        userSettings.setEmailNotificationEnabled(false);
        userSettings.setPushNotificationEnabled(false);
        userSettings.setUpdatedAt(LocalDateTime.now());

        userSettingRepository.save(userSettings);

        return new UserSettingResponse(
                userSettings.getTheme(),
                userSettings.getLanguage(),
                userSettings.isNotificationEnabled(),
                userSettings.getLessonReminderMinutes(),
                userSettings.isEmailNotificationEnabled(),
                userSettings.isPushNotificationEnabled()
        );
    }

    @Override
    @Transactional
    public UserSettingResponse updateUserSettings(String userId, UpdateUserSettingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (request.isNotificationEnabled()) {
            if (!user.getEmailVerified()) {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS);
            }
        }

        UserSettings userSettings = userSettingRepository.findAllByUser_UserId(userId);

        userSettings.setTheme(request.getTheme());
        userSettings.setLanguage(request.getLanguage());
        userSettings.setNotificationEnabled(request.isNotificationEnabled());
        userSettings.setLessonReminderMinutes(request.getLessonReminderMinutes());
        userSettings.setEmailNotificationEnabled(request.isEmailNotificationEnabled());
        userSettings.setPushNotificationEnabled(request.isPushNotificationEnabled());
        userSettings.setUpdatedAt(LocalDateTime.now());

        userSettingRepository.save(userSettings);

        return new UserSettingResponse(
                userSettings.getTheme(),
                userSettings.getLanguage(),
                userSettings.isNotificationEnabled(),
                userSettings.getLessonReminderMinutes(),
                userSettings.isEmailNotificationEnabled(),
                userSettings.isPushNotificationEnabled()
        );
    }
}
