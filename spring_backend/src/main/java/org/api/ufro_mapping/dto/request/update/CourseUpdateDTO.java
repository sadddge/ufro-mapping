package org.api.ufro_mapping.dto.request.update;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDTO {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Role cannot be empty.")
    private String name;
    @Pattern(regexp = "^(?!\\s*$).+", message = "Role cannot be empty.")
    private String code;
    private String description;
    private Integer sct;
}
