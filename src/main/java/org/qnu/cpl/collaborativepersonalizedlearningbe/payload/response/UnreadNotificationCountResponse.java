package org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UnreadNotificationCountResponse {

    private Long unreadCount;

}
