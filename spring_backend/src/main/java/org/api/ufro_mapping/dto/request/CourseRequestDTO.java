package org.api.ufro_mapping.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 45, message = "Name must be less than 45 characters")
    private String name;
    @NotBlank(message = "Code is mandatory")
    private String code;
    private String description;
    private Integer sct;
}
