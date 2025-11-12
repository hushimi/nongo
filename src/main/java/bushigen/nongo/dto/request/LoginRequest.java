package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
    @Schema(description = "Login user name")
    String userName,

    @Schema(description = "Login password")
    String password
) {
}
