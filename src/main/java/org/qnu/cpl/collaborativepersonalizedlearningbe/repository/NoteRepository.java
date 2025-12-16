package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Note;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findAllByTargetId(String targetId);

    @Query("SELECT COALESCE(MAX(n.displayIndex), 0) FROM Note n WHERE n.targetType = :targetType AND n.targetId = :targetId")
    int findMaxDisplayIndexByTarget(@Param("targetType") TargetType targetType,
                                    @Param("targetId") String targetId);
}
