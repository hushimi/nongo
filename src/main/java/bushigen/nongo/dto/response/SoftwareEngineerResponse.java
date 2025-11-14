package bushigen.nongo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SoftwareEngineer APIレスポンス用DTO
 * このクラスはAPIクライアントに返されるデータ構造を表します
 *
 * データベースで生成されるIDフィールドを含みます
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ソフトウェアエンジニアのレスポンスデータ")
public class SoftwareEngineerResponse {

    @Schema(description = "ソフトウェアエンジニアの一意識別子", example = "1")
    private Long id;

    @Schema(description = "ソフトウェアエンジニアの名前", example = "田中太郎")
    private String name;

    @Schema(description = "技術スタックとスキル", example = "Java, Spring Boot, MySQL")
    private String techStack;
}
