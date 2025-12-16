package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {

    List<Resource> findAllByLesson_LessonId(String lessonId);

}
