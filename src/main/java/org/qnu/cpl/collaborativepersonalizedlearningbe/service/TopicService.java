package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTopicRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LessonResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;

public interface TopicService {

    TopicResponse createTopic(CreateTopicRequest request);

    TopicResponse updateTopic(String topicId, UpdateTopicRequest request);

    void deleteTopic(String topicId);

    List<LessonResponse> getLessonsByTopicId(String topicId);

    List<NoteResponse> getNotesByTopicId(String topicId);

}
