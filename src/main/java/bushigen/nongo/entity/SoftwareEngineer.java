package bushigen.nongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Software Engineer entity class.
 * Represents a software engineer in the system.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Software Engineer entity")
public class SoftwareEngineer {

    @Schema(description = "Unique identifier of the software engineer", example = "1")
    private Integer id;

    @Schema(description = "Name of the software engineer", example = "田中太郎")
    private String name;

    @Schema(description = "Technology stack and skills", example = "Java, Spring Boot, MySQL")
    private String techStack;
}
