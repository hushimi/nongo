package bushigen.nongo.tutorial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entity class
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareEngineer {
    private Integer id;
    private String name;
    private String techStack;
}
