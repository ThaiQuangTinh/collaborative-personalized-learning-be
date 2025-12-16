package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Post;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.PostComment;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreatePostCommentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdatePostCommentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.PostCommentResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserPostResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostCommentRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.NotificationService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostCommentService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final NotificationService notificationService;


    @Override
    public List<PostCommentResponse> findAllCommentByPostId(String userId, String postId) {
        if (!postRepository.existsById(postId)) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        List<PostComment> postCommentList = postCommentRepository
                .findAllByPost_PostId(postId);

        List<PostCommentResponse> postCommentResponseList = new ArrayList<>();

        for (PostComment postComment : postCommentList) {
            UserPostResponse userPostResponse = new UserPostResponse(
                    postComment.getUser().getFullname(),
                    userService.getAvatarUrlByUserId(postComment.getUser().getUserId()).getAvatarUrl()
            );

            PostCommentResponse postCommentResponse = new PostCommentResponse();
            postCommentResponse.setUserPostResponse(userPostResponse);
            postCommentResponse.setCommentId(postComment.getCommentId());
            postCommentResponse.setContent(postComment.getContent());
            postCommentResponse.setUpdatedAt(postComment.getUpdatedAt());
            postCommentResponse.setOwnedByCurrentUser(postComment.getUser().getUserId().equals(userId));

            postCommentResponseList.add(postCommentResponse);
        }

        return postCommentResponseList;
    }

    @Override
    public PostCommentResponse createPostComment(String userId, CreatePostCommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCESS_DENIED));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        PostComment postComment = new PostComment();
        postComment.setCommentId(UUIDUtil.generate());
        postComment.setPost(post);
        postComment.setUser(user);
        postComment.setContent(request.getContent());
        postComment.setCreatedAt(LocalDateTime.now());
        postComment.setUpdatedAt(LocalDateTime.now());

        postCommentRepository.save(postComment);


        UserPostResponse userPostResponse = new UserPostResponse(
                user.getFullname(),
                userService.getAvatarUrlByUserId(user.getUserId()).getAvatarUrl()
        );

        if (!post.getUser().getUserId().equals(userId)) {
            notificationService.sendCommentNotification(
                    post.getUser().getUserId(), post.getPostId(), postComment.getCommentId(), userId
            );
        }

        return new PostCommentResponse(
                postComment.getCommentId(),
                userPostResponse,
                postComment.getContent(),
                postComment.getUpdatedAt(),
                postCommentRepository.existsByCommentIdAndUser_UserId(postComment.getCommentId(), userId)
        );
    }

    @Override
    public PostCommentResponse updatePostComment(String userId, String commentId, UpdatePostCommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        if (!postComment.getUser().getUserId().equals(userId)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        postComment.setContent(request.getContent());
        postComment.setUpdatedAt(LocalDateTime.now());

        postCommentRepository.save(postComment);

        UserPostResponse userPostResponse = new UserPostResponse(
                user.getFullname(),
                userService.getAvatarUrlByUserId(user.getUserId()).getAvatarUrl()
        );

        return new PostCommentResponse(
                postComment.getCommentId(),
                userPostResponse,
                postComment.getContent(),
                postComment.getUpdatedAt(),
                postCommentRepository.existsByCommentIdAndUser_UserId(postComment.getCommentId(), userId)
        );
    }

    @Override
    public void deletePostCommentById(String userId, String commentId) {
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        if (!postComment.getUser().getUserId().equals(userId)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        postCommentRepository.delete(postComment);
    }

}
