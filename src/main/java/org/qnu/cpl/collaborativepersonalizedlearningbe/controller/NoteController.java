package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateNoteRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateNoteRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateNoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateNoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NoteService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    // Create note.
    @PostMapping
    public ResponseEntity<ApiResponse<CreateNoteResponse>> createNote(
            @RequestBody CreateNoteRequest request,
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUserId();

        CreateNoteResponse response = noteService.createNote(userId, request);

        return ResponseUtil.created(response, "Note created successfully!", httpRequest);
    }

    // Update note.
    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponse<NoteResponse>> updateNote(
            @PathVariable String noteId,
            @RequestBody UpdateNoteRequest request,
            HttpServletRequest httpRequest) {

        NoteResponse response = noteService.updateNote(noteId, request);

        return ResponseUtil.success(response, "Note updated successfully!",
                HttpStatus.OK, httpRequest);
    }

    // Delete note.
    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Object>> deleteNote(
            @PathVariable String noteId,
            HttpServletRequest httpRequest) {

        noteService.deleteNoteById(noteId);

        return ResponseUtil.success(null, "Note deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

}
