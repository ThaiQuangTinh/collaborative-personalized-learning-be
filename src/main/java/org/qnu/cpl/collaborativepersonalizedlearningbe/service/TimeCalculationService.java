package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Topic;

public interface TimeCalculationService {

    void recalcTopicTime(Topic topic);

    void recalcLearningPathTime(LearningPath path);

}
