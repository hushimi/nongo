package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
  @Schema(description = "パスワードリセットトークン")
  @NotBlank(message = "トークンは必須です")
  String token,

  @Schema(description = "新しいパスワード（8文字以上、数字と特殊文字 !@?_#& を含む必要があります）")
  @NotBlank(message = "パスワードは必須です")
  @Size(min = 8, message = "パスワードは8文字以上で入力してください")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@?_#&]).{8,}$",
    message = "パスワードの形式が正しくありません"
  )
  String password,

  @Schema(description = "パスワード確認")
  @NotBlank(message = "パスワード確認は必須です")
  String confirmPassword
) {
  /**
   * パスワードと確認パスワードが一致することを検証
   */
  @AssertTrue(message = "パスワードと確認パスワードが一致しません")
  public boolean isPasswordMatch() {
    return password.equals(confirmPassword);
  }
}
