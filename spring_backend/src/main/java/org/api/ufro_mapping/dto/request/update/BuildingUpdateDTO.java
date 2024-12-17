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
public class BuildingUpdateDTO {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Name cannot be empty.")
    private String name;
    private String alias;
    private String type;
    private Double latitude;
    private Double longitude;
}
