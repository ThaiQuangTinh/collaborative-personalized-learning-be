package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LessonResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;

import java.util.List;

public interface TopicService {

    TopicResponse createTopic(CreateTopicRequest request);

    TopicResponse updateTopic(String topicId, UpdateTopicRequest request);

    void deleteTopic(String topicId);

    List<LessonResponse> getLessonsByTopicId(String topicId);

    List<NoteResponse> getNotesByTopicId(String topicId);

}
