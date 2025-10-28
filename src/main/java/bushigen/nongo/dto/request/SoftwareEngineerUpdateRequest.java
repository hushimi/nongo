package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SoftwareEngineerUpdateRequest(
    @Schema(description = "ID of the software engineer", example = "1")
    @NotNull(message = "不正なリクエストです")
    Long id,

    @Schema(description = "Name of the software engineer", example = "田中太郎")
    @NotBlank(message = "Nameは必須です")
    @Size(max = 50, message = "Nameは50文字以内で入力してください")
    String name,

    @Schema(description = "Technology stack and skills", example = "Java, Spring Boot, MySQL")
    @NotBlank(message = "Tech Stackは必須です")
    @Size(max = 50, message = "Tech Stackは50文字以内で入力してください")
    String techStack
) {}
