package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Topic;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;

public class TopicMapper {

    public static TopicResponse toResponse(Topic topic) {
        if (topic == null) return null;

        TopicResponse res = new TopicResponse();
        res.setTopicId(topic.getTopicId());
        res.setTitle(topic.getTitle());
        res.setDisplayIndex(topic.getDisplayIndex());
        res.setStartTime(topic.getStartTime());
        res.setEndTime(topic.getEndTime());
        res.setStatus(topic.getStatus());

        return res;
    }

}
