package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
  @Schema(description = "User name (alphanumeric, more than 5 characters)")
  @NotBlank(message = "ユーザ名は必須です")
  @Pattern(regexp = "^[a-zA-Z0-9]{6,}$",
    message = "ユーザ名は6文字以上の英数字で入力してください"
  )
  String user_name,

  @Schema(description = "Email address")
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスは有効な形式で入力してください")
  String email,

  @Schema(description = "Password (at least 8 characters, must include number and special character !@?_#&)")
  @NotBlank(message = "パスワードは必須です")
  @Size(min = 8, message = "パスワードは8文字以上で入力してください")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@?_#&]).{8,}$",
    message = "パスワードの形式が正しくありません"
  )
  String password
) {
}
