package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post, String> {

    List<Post> findAllByIsDeletedFalseOrderByCreatedAtDesc();

}
