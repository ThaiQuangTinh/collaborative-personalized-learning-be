package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.List;
import java.util.Optional;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUserIdAndRole(String userId, Role role);

    List<User> findAllByIsDeletedFalseOrderByCreatedAtDesc();
}
