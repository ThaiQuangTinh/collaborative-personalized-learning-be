package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import java.util.Optional;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {

    void deleteByUser(User user);

    Optional<VerificationCode> findByUserUserIdAndCode(String userId, String code);

}
