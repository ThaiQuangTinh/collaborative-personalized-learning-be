package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminPostResponse;

public interface AdminPostService {

    List<AdminPostResponse> getAllPosts(String adminId);

    AdminPostResponse getPostById(String adminId, String postId);

    void deletePostById(String adminId, String postId);

}
