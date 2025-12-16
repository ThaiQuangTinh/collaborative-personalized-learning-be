package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateTagResponse;

import java.util.List;

public interface TagService {

    CreateTagResponse createTag(String userId, CreateTagRequest request);

    UpdateTagResponse updateTag(String tagId, UpdateTagRequest request);

    void deleteTag(String tagId);

    List<TagResponse> getAllTagsByUser(String userId);

}
