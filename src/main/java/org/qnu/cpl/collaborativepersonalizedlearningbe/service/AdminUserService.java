package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.AdminUserResponse;

import java.util.List;

public interface AdminUserService {

    List<AdminUserResponse> getAllUser(String adminId);

    AdminUserResponse getUserById(String adminId, String userId);

    void deleteUserById(String adminId, String userId);

}
