package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminPostResponse;

import java.util.List;

public interface AdminPostService {

    List<AdminPostResponse> getAllPosts(String adminId);

    AdminPostResponse getPostById(String adminId, String postId);

    void deletePostById(String adminId, String postId);

}
