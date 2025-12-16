package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.VerificationCode;

public interface VerificationCodeService {

    void deleteByUser(User user);

    void deleteVerificationCode(VerificationCode verificationCode);

    void saveVerificationCode(VerificationCode verificationCode);

    VerificationCode findByUserIdAndCode(String userId, String code);

}
