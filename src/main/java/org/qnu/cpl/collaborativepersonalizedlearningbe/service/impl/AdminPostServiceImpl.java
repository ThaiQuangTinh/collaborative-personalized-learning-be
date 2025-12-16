package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Post;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminPostResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.AdminPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    public List<AdminPostResponse> getAllPosts(String adminId) {
        if (!userRepository.existsByUserIdAndRole(adminId, Role.ADMIN)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        List<Post> postList = postRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();

        List<AdminPostResponse> adminPostResponseList = new ArrayList<>();
        for (Post post : postList) {
            AdminPostResponse adminPostResponse = new AdminPostResponse(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getUser().getFullname(),
                    post.getLikeCount(),
                    post.getCommentCount(),
                    post.getCreatedAt()
            );

            adminPostResponseList.add(adminPostResponse);
        }

        return adminPostResponseList;
    }

    @Override
    public AdminPostResponse getPostById(String adminId, String postId) {
        if (!userRepository.existsByUserIdAndRole(adminId, Role.ADMIN)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        return new AdminPostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getFullname(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void deletePostById(String adminId, String postId) {
        if (!userRepository.existsByUserIdAndRole(adminId, Role.ADMIN)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        post.setIsDeleted(true);
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
    }
}
