package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Post;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreatePostRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdatePostRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostLikeRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostCommentService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.PostService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;

    private final UserService userService;

    private final PostCommentService postCommentService;

    @Override
    public List<PostResponse> getAllPost(String userId) {
        List<Post> postList = postRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();

        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post post : postList) {
            UserPostResponse userPostResponse = new UserPostResponse(
                    post.getUser().getFullname(),
                    userService.getAvatarUrlByUserId(post.getUser().getUserId()).getAvatarUrl()
            );

            List<PostCommentResponse> postCommentResponseList =
                    postCommentService.findAllCommentByPostId(userId, post.getPostId());

            PostResponse postResponse = new PostResponse();
            postResponse.setPostId(post.getPostId());
            postResponse.setTitle(post.getTitle());
            postResponse.setContent(post.getContent());
            postResponse.setExternalLink(post.getExternalLink());
            postResponse.setLikeCount(post.getLikeCount());
            postResponse.setCommentCount(post.getCommentCount());
            postResponse.setCreateAt(post.getCreatedAt());
            postResponse.setUpdatedAt(post.getUpdatedAt());
            postResponse.setUserPostResponse(userPostResponse);
            postResponse.setPostCommentResponses(postCommentResponseList);
            postResponse.setOwnedByCurrentUser(post.getUser().getUserId().equals(userId));
            postResponse.setLiked(postLikeRepository.existsByUser_UserIdAndPost_PostId(userId, post.getPostId()));

            postResponseList.add(postResponse);
        }

        return postResponseList;
    }

    @Override
    public PostResponse createPost(String userId, CreatePostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post();
        post.setUser(user);
        post.setPostId(UUIDUtil.generate());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
//        post.setSharedLearningPath();
        post.setExternalLink(request.getExternalLink());
        post.setSharedLearningPath(null);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        UserPostResponse userPostResponse = new UserPostResponse(
                post.getUser().getFullname(),
                userService.getAvatarUrlByUserId(userId).getAvatarUrl()
        );

        PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getPostId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setExternalLink(post.getExternalLink());
        postResponse.setLikeCount(post.getLikeCount());
        postResponse.setCommentCount(0);
        postResponse.setUpdatedAt(post.getUpdatedAt());
        postResponse.setUserPostResponse(userPostResponse);
        postResponse.setPostCommentResponses(new ArrayList<>());
        postResponse.setOwnedByCurrentUser(true);
        postResponse.setLiked(false);

        return postResponse;
    }

    @Override
    public PostResponse updatePost(String userId, String postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (!userId.equals(post.getUser().getUserId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setExternalLink(request.getExternalLink());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        UserPostResponse userPostResponse = new UserPostResponse(
                post.getUser().getFullname(),
                userService.getAvatarUrlByUserId(userId).getAvatarUrl()
        );

        PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getPostId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setExternalLink(post.getExternalLink());
        postResponse.setLikeCount(post.getLikeCount());
        postResponse.setCommentCount(post.getCommentCount());
        postResponse.setUpdatedAt(post.getUpdatedAt());
        postResponse.setUserPostResponse(userPostResponse);
        postResponse.setPostCommentResponses(new ArrayList<>());
        postResponse.setOwnedByCurrentUser(post.getUser().getUserId().equals(userId));
        postResponse.setLiked(postLikeRepository.existsByUser_UserIdAndPost_PostId(userId, post.getPostId()));

        return postResponse;
    }

    @Override
    public void deletePostById(String userId, String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (!(post.getUser().getUserId()).equals(userId)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        post.setIsDeleted(true);
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
    }
}
