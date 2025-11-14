package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
    @Schema(description = "ログインユーザー名")
    String userName,

    @Schema(description = "ログインパスワード")
    String password
) {
}
