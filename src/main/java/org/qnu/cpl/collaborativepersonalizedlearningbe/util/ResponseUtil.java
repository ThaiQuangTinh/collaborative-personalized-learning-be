package org.qnu.cpl.collaborativepersonalizedlearningbe.util;

import jakarta.servlet.http.HttpServletRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiError;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(
            T data, String message, HttpStatus status, HttpServletRequest request) {

        ApiResponse<T> response = ApiResponse.<T>builder()
                .status(status.value())
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(
            T data, ApiError err, String message, HttpStatus status, HttpServletRequest request) {

        ApiResponse<T> response = ApiResponse.<T>builder()
                .status(status.value())
                .success(false)
                .message(message)
                .error(err)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(
            T data, HttpServletRequest request) {
        return success(data, "Request processed successfully", HttpStatus.OK, request);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(
            T data, String message, HttpServletRequest request) {
        return success(data, message, HttpStatus.CREATED, request);
    }

}
