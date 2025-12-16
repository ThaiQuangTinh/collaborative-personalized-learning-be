package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Post;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostLike;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostLikeId;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostLikeRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NotificationService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostLikeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    @Override
    public void likePost(String userId, String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        PostLikeId id = new PostLikeId(postId, userId);
        if (postLikeRepository.existsById(id)) {
            throw new AppException(ErrorCode.ALREADY_LIKED_POST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PostLike like = new PostLike();
        like.setPostLikeId(id);
        like.setPost(post);
        like.setUser(user);
        like.setCreatedAt(LocalDateTime.now());

        postLikeRepository.save(like);

        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);

        if (!post.getUser().getUserId().equals(userId)) {
            // Send noti to owner of post
            notificationService.sendLikeNotification(post.getUser().getUserId(), post.getPostId(), userId);
        }
    }

    @Override
    public void unlikePost(String userId, String postId) {
        PostLikeId id = new PostLikeId(postId, userId);

        if (!postLikeRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOT_LIKED_YET);
        }

        postLikeRepository.deleteById(id);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
        postRepository.save(post);
    }

}
