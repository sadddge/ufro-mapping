package org.api.ufro_mapping.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomRequestDTO {
    @NotNull(message = "Building id cannot be empty")
    @Positive(message = "Building id must be greater than 0")
    private Long buildingId;
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;
}
