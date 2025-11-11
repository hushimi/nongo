package bushigen.nongo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class for sending emails.
 * Handles email sending functionality including verification emails.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;

  @Value("${app.base-url:http://localhost:8081}")
  private String baseUrl;

  /**
   * Send email verification message to user
   * @param email User's email address
   * @param userName User's username
   * @param token Verification token
   */
  public void sendVerificationEmail(String email, String userName, String token) {
    try {
      String verificationUrl = baseUrl + "/verify-email?token=" + token;

      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(email);
      message.setSubject("アカウント認証メール");
      message.setText(buildVerificationEmailBody(userName, verificationUrl));

      mailSender.send(message);
      log.info("Verification email sent to: {}", email);
    } catch (Exception e) {
      log.error("Failed to send verification email to: {}", email, e);
      throw new RuntimeException("メール送信に失敗しました", e);
    }
  }

  /**
   * Build email body text for verification email
   * @param userName User's username
   * @param verificationUrl Verification URL
   * @return Email body text
   */
  private String buildVerificationEmailBody(String userName, String verificationUrl) {
    return String.format(
      "%s 様\n\n" +
      "この度は、nongoアプリケーションにご登録いただき、ありがとうございます。\n\n" +
      "アカウントを有効化するには、以下のリンクをクリックしてください：\n\n" +
      "%s\n\n" +
      "このリンクは24時間有効です。\n\n" +
      "もしこのメールに心当たりがない場合は、このメールを無視してください。\n\n" +
      "よろしくお願いいたします。\n" +
      "nongo運営チーム",
      userName,
      verificationUrl
    );
  }
}
