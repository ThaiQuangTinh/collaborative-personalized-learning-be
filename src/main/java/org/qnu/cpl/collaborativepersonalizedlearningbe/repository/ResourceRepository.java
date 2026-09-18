package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, String> {

    List<Resource> findAllByLesson_LessonId(String lessonId);

}
