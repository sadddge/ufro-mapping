package org.ufromap.feature.classrooms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaRequestDTO {
    private int edificioId;
    private String nombre;
}
