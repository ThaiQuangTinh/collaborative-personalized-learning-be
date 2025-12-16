package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {

    void deleteByUser(User user);

    Optional<VerificationCode> findByUserUserIdAndCode(String userId, String code);

}
