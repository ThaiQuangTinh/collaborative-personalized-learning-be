package org.qnu.cpl.collaborativepersonalizedlearningbe.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiError;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.UserInfo;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex,
                                                                HttpServletRequest request) {

        ErrorCode code = ex.getErrorCode();

        ApiError error = ApiError.builder()
                .code(code.getCode())
                .name(code.getStatus().getReasonPhrase())
                .message(code.getMessage())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(code.getStatus().value())
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFound(UsernameNotFoundException ex,
                                                                    HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .code(ErrorCode.INVALID_CREDENTIALS.getCode())
                .name("Authentication Failed")
                .message("Wrong username or password!")
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.UNAUTHORIZED.value()) // 401
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ApiResponse<UserInfo>> handleEmailNotVerified(
            EmailNotVerifiedException ex, HttpServletRequest request) {

        ApiError err = ApiError.builder()
                .code(ErrorCode.EMAIL_NOT_VERIFIED.getCode())
                .name("Forbidden")
                .message(ex.getMessage())
                .build();

        UserInfo userInfo = UserInfo.builder()
                .userId(ex.getUserId())
                .email(ex.getEmail())
                .build();

        return ResponseUtil.error(userInfo, err, ex.getMessage(),
                HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .code("METHOD_NOT_ALLOWED")
                .name("Method Not Allowed")
                .message(ex.getMessage())
                .build();

        return ResponseUtil.error(null, error, ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception ex, HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .code("INTERNAL_ERROR")
                .name("Internal Server Error")
                .message("An unknown error occurred!")
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .success(false)
                .error(error)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
