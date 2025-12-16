package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {

    @Id
    @Column(name = "code_id", columnDefinition = "CHAR(36)")
    private String codeId;

    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VerificationCodeType type;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum VerificationCodeType {
        EMAIL_VERIFICATION,
        PASSWORD_RESET
    }
}
