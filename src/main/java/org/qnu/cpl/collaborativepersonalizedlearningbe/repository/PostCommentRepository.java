package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, String> {

    List<PostComment> findAllByPost_PostId(String postId);

    boolean existsByCommentIdAndUser_UserId(String commentId, String userId);

}
