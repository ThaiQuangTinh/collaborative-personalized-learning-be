package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Language;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Theme;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSettingResponse {

    private Theme theme = Theme.AUTO;

    private Language language = Language.VI;

    private boolean notificationEnabled = true;

    private Integer lessonReminderMinutes = 60;

    private boolean emailNotificationEnabled = true;

    private boolean pushNotificationEnabled = true;

}
