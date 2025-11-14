package bushigen.nongo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;

/**
 * メール送信を行うサービスクラス
 * 認証メールなどのメール送信機能を提供します
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;

  @Value("${app.base-url:http://localhost:8081}")
  private String baseUrl;

  /**
   * ユーザーに認証メールを送信する
   * @param email ユーザーのメールアドレス
   * @param userName ユーザー名
   * @param token 認証トークン
   */
  public void sendVerificationEmail(String email, String userName, String token) {
    try {
      String verificationUrl = baseUrl + "/verify-email?token=" + token;

      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      if (email != null) {
        helper.setTo(email);
      }
      helper.setSubject("【nongo】アカウント認証のお願い");
      String htmlContent = buildVerificationEmailHtml(userName, verificationUrl);
      if (htmlContent != null) {
        helper.setText(htmlContent, true);
      }

      mailSender.send(mimeMessage);
      log.info("Verification email sent to: {}", email);
    } catch (MessagingException e) {
      log.error("Failed to send verification email to: {}", email, e);
      throw new RuntimeException("メール送信に失敗しました", e);
    }
  }

  /**
   * 認証メールのHTML本文を構築する
   * @param userName ユーザー名
   * @param verificationUrl 認証URL
   * @return HTMLメール本文
   */
  private String buildVerificationEmailHtml(String userName, String verificationUrl) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put("userName", userName);
    replacements.put("verificationUrl", verificationUrl);

    return composeEmail(
      "templates/email/content/verification-email-content.html",
      "アカウント認証",
      replacements
    );
  }

  /**
   * リソースからHTMLメールテンプレートを読み込む
   * @param templatePath リソース内のテンプレートファイルパス
   * @return HTMLテンプレートの内容
   */
  private String loadEmailTemplate(String templatePath) {
    if (templatePath == null || templatePath.isEmpty()) {
      throw new IllegalArgumentException("テンプレートパスがnullまたは空です");
    }

    try {
      ClassPathResource resource = new ClassPathResource(templatePath);
      String content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
      if (content == null) {
        throw new IOException("テンプレートの内容がnullです");
      }
      return content;
    } catch (IOException e) {
      log.error("Failed to load email template: {}", templatePath, e);
      throw new RuntimeException("メールテンプレートの読み込みに失敗しました", e);
    }
  }

  /**
   * ベーステンプレートとコンテンツを組み合わせてメールHTMLを構築
   * @param contentPath コンテンツテンプレートのパス
   * @param title メールのタイトル
   * @param replacements 置換するキーと値のマップ
   * @return 完成したHTMLメール
   */
  private String composeEmail(String contentPath, String title, Map<String, String> replacements) {
    // ベーステンプレートを読み込む
    String baseTemplate = loadEmailTemplate("templates/email/base-email.html");
    // コンテンツを読み込む
    String content = loadEmailTemplate(contentPath);

    // コンテンツ内のプレースホルダーを置換
    for (Map.Entry<String, String> entry : replacements.entrySet()) {
      content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
    }

    // ベーステンプレートにコンテンツを挿入
    String finalHtml = baseTemplate
      .replace("{{title}}", title)
      .replace("{{content}}", content);

    return finalHtml;
  }

}
