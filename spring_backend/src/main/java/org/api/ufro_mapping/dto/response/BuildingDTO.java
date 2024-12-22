package org.api.ufro_mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDTO {
    private Long id;
    private String name;
    private String alias;
    private String type;
    private Double latitude;
    private Double longitude;
    private Set<ClassroomDTO> classrooms;
}
