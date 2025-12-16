package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminUserResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.PostRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.AdminUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    public List<AdminUserResponse> getAllUser(String adminId) {
        if (!userRepository.existsByUserIdAndRole(adminId, Role.ADMIN)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        List<User> userList = userRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();

        List<AdminUserResponse> adminUserResponseList = new ArrayList<>();

        for (User user : userList) {
            AdminUserResponse adminUserResponse = new AdminUserResponse(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFullname(),
                    user.getRole(),
                    user.getEmailVerified(),
                    user.getCreatedAt()
            );

            adminUserResponseList.add(adminUserResponse);
        }

        return adminUserResponseList;
    }

    @Override
    public AdminUserResponse getUserById(String adminId, String userId) {
        if (!userRepository.existsByUserIdAndRole(adminId, Role.ADMIN)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return new AdminUserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullname(),
                user.getRole(),
                user.getEmailVerified(),
                user.getCreatedAt()
        );
    }

    @Override
    public void deleteUserById(String adminId, String userId) {
        if (!userRepository.existsByUserIdAndRole(adminId, Role.ADMIN)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setIsDeleted(true);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

}
