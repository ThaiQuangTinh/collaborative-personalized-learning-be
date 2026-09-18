package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request;

import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Language;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Theme;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserSettingRequest {

    private Theme theme = Theme.AUTO;

    private Language language = Language.VI;

    private boolean notificationEnabled = true;

    private Integer lessonReminderMinutes = 60;

    private boolean emailNotificationEnabled = true;

    private boolean pushNotificationEnabled = true;

}
