package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(
  @Schema(description = "パスワードリセット用のメールアドレス")
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスは有効な形式で入力してください")
  String email
) {
}
