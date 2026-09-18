package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {

    List<Post> findAllByIsDeletedFalseOrderByCreatedAtDesc();

}
