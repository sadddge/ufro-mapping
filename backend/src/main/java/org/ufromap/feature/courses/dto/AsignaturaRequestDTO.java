package org.ufromap.feature.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignaturaRequestDTO {
    private String nombre;
    private String codigo;
    private String descripcion;
    private Integer sct;
}
