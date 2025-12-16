package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.VerificationCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.VerificationCodeRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.VerificationCodeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    public void deleteByUser(User user) {
        verificationCodeRepository.deleteByUser(user);
    }

    @Override
    public void deleteVerificationCode(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }

    @Override
    public void saveVerificationCode(VerificationCode verificationCode) {
        verificationCodeRepository.save(verificationCode);
    }

    @Override
    public VerificationCode findByUserIdAndCode(String userId, String code) {
        return verificationCodeRepository.findByUserUserIdAndCode(
                userId, code
        ).orElseThrow(() -> new AppException(ErrorCode.INVALID_VERIFICATION_CODE));
    }

}
