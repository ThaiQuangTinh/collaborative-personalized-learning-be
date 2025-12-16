package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

public interface PostLikeService {

    void likePost(String userId, String postId);

    void unlikePost(String userId, String postId);

}
