package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateNoteRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateNoteRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateNoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;

public interface NoteService {

    CreateNoteResponse createNote(String userId, CreateNoteRequest request);

    NoteResponse updateNote(String noteId, UpdateNoteRequest request);

    void deleteNoteById(String noteId);

}
