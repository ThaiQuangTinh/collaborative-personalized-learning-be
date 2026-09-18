package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

    boolean existsByUser_UserIdAndTagName(String userId, String tagName);

    boolean existsByTextColor(String textColor);

    List<Tag> findAllByUser_UserId(String userId);

}
