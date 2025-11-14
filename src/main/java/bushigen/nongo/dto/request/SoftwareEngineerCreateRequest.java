package bushigen.nongo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * SoftwareEngineer新規作成用DTO
 * 注：IDフィールドはデータベースで自動生成されるため含まれていません
 */
public record SoftwareEngineerCreateRequest(
    @Schema(description = "ソフトウェアエンジニアの名前", example = "田中太郎")
    @NotBlank(message = "Nameは必須です")
    @Size(max = 50, message = "Nameは50文字以内で入力してください")
    String name,

    @Schema(description = "技術スタックとスキル", example = "Java, Spring Boot, MySQL")
    @NotBlank(message = "Tech Stackは必須です")
    @Size(max = 50, message = "Tech Stackは50文字以内で入力してください")
    String techStack
) {}