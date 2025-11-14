package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
  @Schema(description = "ユーザー名（英数字、6文字以上）")
  @NotBlank(message = "ユーザ名は必須です")
  @Pattern(regexp = "^[a-zA-Z0-9]{6,}$",
    message = "ユーザ名は6文字以上の英数字で入力してください"
  )
  String userName,

  @Schema(description = "メールアドレス")
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスは有効な形式で入力してください")
  String email,

  @Schema(description = "パスワード（8文字以上、数字と特殊文字 !@?_#& を含む必要があります）")
  @NotBlank(message = "パスワードは必須です")
  @Size(min = 8, message = "パスワードは8文字以上で入力してください")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@?_#&]).{8,}$",
    message = "パスワードの形式が正しくありません"
  )
  String password
) {
}
