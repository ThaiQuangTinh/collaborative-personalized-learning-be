package org.qnu.cpl.collaborativepersonalizedlearningbe.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // ---- AUTH  ----
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "USERNAME_ALREADY_EXISTS", "Username is already taken"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS", "Email is already registered"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "INVALID_EMAIL_FORMAT", "Email format is invalid"),
    WEAK_PASSWORD(HttpStatus.BAD_REQUEST, "WEAK_PASSWORD", "Password must contain at least 8 characters"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Username or password is incorrect"),
    EMAIL_ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_VERIFIED", "Email has already been verified"),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "INVALID_VERIFICATION_CODE", "Invalid verification code"),
    VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "VERIFICATION_CODE_EXPIRED", "Verification code has expired"),
    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "EMAIL_NOT_VERIFIED", "Email has not been verified yet"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "Access token has expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "Token is invalid"),
    INVALID_PHONE_FORMAT(HttpStatus.BAD_REQUEST, "INVALID_PHONE_FORMAT", "Phone number format is invalid"),

    // ---- SYSTEM ----
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Unexpected server error"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "You do not have permission to access this resource"),


    // ---- USER ----
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User not found"),

    // ---- ADMIN ----
    ADMIN_ONLY(HttpStatus.FORBIDDEN, "ADMIN_ONLY", "Only admin is allowed to access this resource"),

    // ---- TAG ----
    TAG_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "TAG_NAME_ALREADY_EXISTS", "Tag name already exists for this user"),
    TAG_COLOR_ALREADY_EXISTS(HttpStatus.CONFLICT, "TAG_COLOR_ALREADY_EXISTS", "Tag color already exists for this user"),
    INVALID_TAG_COLOR(HttpStatus.BAD_REQUEST, "INVALID_TAG_COLOR", "Tag color is invalid (must be HEX #RRGGBB or #RRGGBBAA)"),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "TAG_NOT_FOUND", "Tag not found"),
    TAG_NOT_ATTACHED_TO_LEARNING_PATH(HttpStatus.BAD_REQUEST, "TAG_NOT_ATTACHED_TO_LEARNING_PATH", "Tag not attached to this learning path!"),

    // ---- LEARNING PATH ----
    LEARNING_PATH_TITLE_ALREADY_EXISTS(HttpStatus.CONFLICT, "LEARNING_PATH_TITLE_ALREADY_EXISTS", "Learning path title already exists for this user"),
    LEARNING_PATH_NOT_FOUND(HttpStatus.NOT_FOUND, "LEARNING_PATH_NOT_FOUND", "Learning path not found"),
    LEARNING_PATH_ALREADY_EXISTS(HttpStatus.CONFLICT, "LEARNING_PATH_ALREADY_EXISTS", "Learning path already exists"),


    // ---- SHARE LEARNING PATH ----
    SHARE_LEARNING_PATH_NOT_FOUND(HttpStatus.NOT_FOUND, "SHARE_LEARNING_PATH_NOT_FOUND", "Share learning path not found"),

    // ---- NOTE ----
    NOTE_TITLE_ALREADY_EXISTS(HttpStatus.CONFLICT, "NOTE_TITLE_ALREADY_EXISTS", "Note title already exists for this user"),
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTE_NOT_FOUND", "Note not found"),
    INVALID_NOTE_TARGET(HttpStatus.BAD_REQUEST, "INVALID NOTE TARGET", "Invalid note target"),

    // ---- TOPIC ----
    TOPIC_TITLE_ALREADY_EXISTS(HttpStatus.CONFLICT, "TOPIC_TITLE_ALREADY_EXISTS", "Topic title already exists for this user"),
    TOPIC_NOT_FOUND(HttpStatus.NOT_FOUND, "TOPIC_NOT_FOUND", "Topic not found"),

    // ---- LESSON ----
    LESSON_TITLE_ALREADY_EXISTS(HttpStatus.CONFLICT, "LESSON_TITLE_ALREADY_EXISTS", "Lesson title already exists for this user"),
    LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, "LESSON_NOT_FOUND", "Lesson not found"),

    // ---- PROGRESS ----
    PROGRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "PROGRESS_NOT_FOUND", "Progress not found"),

    // ---- RESOURCES ----
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "Resource not found"),

    // ---- FILE UPLOAD ----
    FILE_SIZE_TOO_LARGE(HttpStatus.BAD_REQUEST, "FILE_SIZE_TOO_LARGE", "File size exceeds the allowed limit"),
    FILE_TYPE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "FILE_TYPE_NOT_ALLOWED", "File type is not allowed"),

    // ---- MINIO ----
    MINIO_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MINIO_UPLOAD_FAILED", "Failed to upload file to MinIO"),
    MINIO_GET_FAILED(HttpStatus.NOT_FOUND, "MINIO_GET_FAILED", "Failed to get file from MinIO"),
    MINIO_URL_GENERATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MINIO_URL_GENERATE_FAILED", "Failed to generate presigned URL"),
    MINIO_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MINIO_DELETE_FAILED", "Failed to delete file from MinIO"),


    // ---- POST ----
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "Post not found"),
    ALREADY_LIKED_POST(HttpStatus.BAD_REQUEST, "ALREADY_LIKED_POST", "You already liked this post"),
    NOT_LIKED_YET(HttpStatus.BAD_REQUEST, "NOT_LIKED_YET", "You have not liked this post yet"),

    // ----- COMMENT ----
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "Comment not found"),


    // ---- NOTIFICATION ----
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND", "Notification not found"),


    // ---- FILE ----
    FILE_PARSE_ERROR(HttpStatus.BAD_REQUEST, "FILE_PARSE_ERROR", "File parse error");


    private final HttpStatus status;

    private final String code;

    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
