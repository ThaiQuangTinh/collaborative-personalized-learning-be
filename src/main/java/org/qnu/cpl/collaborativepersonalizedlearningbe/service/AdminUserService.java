package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminUserResponse;

public interface AdminUserService {

    List<AdminUserResponse> getAllUser(String adminId);

    AdminUserResponse getUserById(String adminId, String userId);

    void deleteUserById(String adminId, String userId);

}
