package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Note;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;

public class NoteMapper {

    public static NoteResponse toResponse(Note note) {
        if (note == null) return null;

        NoteResponse res = new NoteResponse();
        res.setNoteId(note.getNoteId());
        res.setTargetType(note.getTargetType());
        res.setTargetId(note.getTargetId());
        res.setTitle(note.getTitle());
        res.setContent(note.getContent());
        res.setDisplayIndex(note.getDisplayIndex());

        return res;
    }

}
