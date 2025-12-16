package org.qnu.cpl.collaborativepersonalizedlearningbe.service;


public interface MailService {

    void sendHtmlMail(String to, String subject, String htmlContent);

    void sendVerificationEmail(String to, String code);

    void sendPasswordResetEmail(String to, String code);

}
