package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreatePostCommentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdatePostCommentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.PostCommentResponse;

import java.util.List;

public interface PostCommentService {

    List<PostCommentResponse> findAllCommentByPostId(String userId, String postId);

    PostCommentResponse createPostComment(String userId, CreatePostCommentRequest request);

    PostCommentResponse updatePostComment(String userId, String commentId, UpdatePostCommentRequest request);

    void deletePostCommentById(String userId, String commentId);

}
