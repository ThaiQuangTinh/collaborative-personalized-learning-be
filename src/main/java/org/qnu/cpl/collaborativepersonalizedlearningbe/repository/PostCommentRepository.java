package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, String> {

    List<PostComment> findAllByPost_PostId(String postId);

    boolean existsByCommentIdAndUser_UserId(String commentId, String userId);

}
