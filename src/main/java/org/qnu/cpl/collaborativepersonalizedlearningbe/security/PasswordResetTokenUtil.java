package org.qnu.cpl.collaborativepersonalizedlearningbe.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.constants.Constants;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenUtil {

    private final JwtTokenUtil jwtTokenUtil;

    private final long resetTokenExpiration = Constants.VERIFICATION_CODE_EXPIRATION_MINUTES * 60 * 1000; // 5 phút

    public String generateResetToken(User user) {
        return jwtTokenUtil.generateToken(
                Map.of("type", "password_reset"),
                user.getUserId(),
                resetTokenExpiration
        );
    }

    public void validateResetToken(String token) {
        jwtTokenUtil.validateToken(token);

        Claims claims = jwtTokenUtil.getAllClaims(token);
        String type = (String) claims.get("type");
        if (!"password_reset".equals(type)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
