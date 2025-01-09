package org.ufromap.feature.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignaturaDTO {
    private int id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private int sct;
}
