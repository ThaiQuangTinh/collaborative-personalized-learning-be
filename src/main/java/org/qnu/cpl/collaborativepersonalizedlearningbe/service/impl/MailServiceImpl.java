package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendVerificationEmail(String to, String code) {
        String subject = "Xác minh địa chỉ email của bạn";
        String html = buildEmailTemplate(
                "Xác minh email",
                "Cảm ơn bạn đã đăng ký tài khoản tại <b>Rabbit Learning</b>",
                code,
                "Mã này sẽ hết hạn sau 5 phút. Vui lòng không chia sẻ mã này cho bất kỳ ai."
        );

        sendHtmlMail(to, subject, html);
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String to, String code) {
        String subject = "Đặt lại mật khẩu của bạn";
        String html = buildEmailTemplate(
                "Đặt lại mật khẩu",
                "Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản <b>Rabbit Learning</b> của mình.",
                code,
                "Mã có hiệu lực trong 5 phút. Nếu bạn không yêu cầu, vui lòng bỏ qua email này."
        );

        sendHtmlMail(to, subject, html);
    }

    @Async
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("thaitinh240803@gmail.com", "Rabbit Learning");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Gửi email thất bại: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thiết lập email: " + e.getMessage(), e);
        }
    }

    private String buildEmailTemplate(String title, String message, String code, String footer) {
        return """
                <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 520px; margin: auto;
                            border: 1px solid #eee; border-radius: 12px; overflow: hidden;
                            box-shadow: 0 6px 16px rgba(0,0,0,0.08);">
                    <!-- Header -->
                    <div style="background: linear-gradient(90deg, #F7B733 0%%, #FC4A1A 100%%);
                                color: white; padding: 10px 18px; text-align: center;">
                        <h2 style="margin: 0; font-size: 22px; letter-spacing: 0.5px;">%s</h2>
                    </div>
                    <!-- Body -->
                    <div style="padding: 28px; color: #333;">
                        <p style="font-size: 16px; line-height: 1.6;">%s</p>
                        <div style="text-align: center; margin: 32px 0;">
                            <span style="font-size: 28px; font-weight: bold; color: #FC4A1A; letter-spacing: 4px;">
                                %s
                            </span>
                        </div>
                        <p style="font-size: 14px; color: #666; line-height: 1.5;">%s</p>
                    </div>
                    <!-- Footer -->
                    <div style="background-color: #fef6f2; padding: 14px; text-align: center;
                                font-size: 12px; color: #999;">
                        © 2025 <b>Rabbit Learning</b>. All rights reserved.
                    </div>
                </div>
                """.formatted(title, message, code, footer);
    }
}