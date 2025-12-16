package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreatePostRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdatePostRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreatePostResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.PostResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdatePostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getAllPost(String userId);

    PostResponse createPost(String userId, CreatePostRequest request);

    PostResponse updatePost(String userId, String postId, UpdatePostRequest request);

    void deletePostById(String userId, String postId);

}
