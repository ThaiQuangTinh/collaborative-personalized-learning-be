package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Note;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.TargetType;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateNoteRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateNoteRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateNoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NoteService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    private final LearningPathRepository learningPathRepository;

    private final TopicRepository topicRepository;

    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public CreateNoteResponse createNote(String userId, CreateNoteRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        TargetType targetType;
        try {
            targetType = TargetType.valueOf(request.getTargetType());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_NOTE_TARGET);
        }

        switch (targetType) {
            case PATH -> {
                if (!learningPathRepository.existsById(request.getTargetId())) {
                    throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
                }
            }
            case TOPIC -> {
                if (!topicRepository.existsById(request.getTargetId())) {
                    throw new AppException(ErrorCode.TOPIC_NOT_FOUND);
                }
            }
            case LESSON -> {
                if (!lessonRepository.existsById(request.getTargetId())) {
                    throw new AppException(ErrorCode.LESSON_NOT_FOUND);
                }
            }
            default -> throw new AppException(ErrorCode.INVALID_NOTE_TARGET);
        }

//        if (noteRepository.existsByUser_UserIdAndTargetTypeAndTargetIdAndTitle(
//                userId, targetType, request.getTargetId(), request.getTitle())) {
//            throw new AppException(ErrorCode.NOTE_TITLE_ALREADY_EXISTS);
//        }

        Integer maxDisplayIndex = noteRepository.findMaxDisplayIndexByTarget(targetType, request.getTargetId());

        Note note = Note.builder()
                .noteId(UUIDUtil.generate())
                .user(user)
                .targetType(TargetType.fromString(request.getTargetType()))
                .targetId(request.getTargetId())
                .title(request.getTitle())
                .content(request.getContent())
                .displayIndex(maxDisplayIndex + 1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        noteRepository.save(note);

        return new CreateNoteResponse(
                note.getNoteId(),
                note.getTargetType().toString(),
                note.getTargetId(),
                note.getTitle(),
                note.getContent(),
                note.getDisplayIndex()
        );
    }


    @Override
    @Transactional
    public NoteResponse updateNote(String noteId, UpdateNoteRequest request) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTE_NOT_FOUND));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUpdatedAt(LocalDateTime.now());

        return new NoteResponse(
                note.getNoteId(),
                note.getTargetType(),
                note.getTargetId(),
                note.getTitle(),
                note.getContent(),
                note.getDisplayIndex()
        );
    }

    @Override
    @Transactional
    public void deleteNoteById(String noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new AppException(ErrorCode.NOTE_NOT_FOUND);
        }

        noteRepository.deleteById(noteId);
    }

}
