package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostLike;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    boolean existsByUser_UserIdAndPost_PostId(String userId, String postId);

}
