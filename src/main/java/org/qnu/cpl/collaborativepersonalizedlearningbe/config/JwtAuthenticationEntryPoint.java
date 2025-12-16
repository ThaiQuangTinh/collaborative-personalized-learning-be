package org.qnu.cpl.collaborativepersonalizedlearningbe.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.TokenException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiError;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ErrorCode code = ErrorCode.INVALID_TOKEN;

        if (authException instanceof TokenException te) {
            code = te.getErrorCode();
        }

        ApiError error = ApiError.builder()
                .code(code.getCode())
                .name(code.getStatus().getReasonPhrase())
                .message(authException.getMessage() != null ? authException.getMessage() : "Token missing or invalid")
                .build();

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}