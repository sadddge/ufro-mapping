package org.ufromap.feature.classrooms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ufromap.feature.buildings.dto.EdificioDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaDTO {
    private int id;
    private EdificioDTO edificio;
    private String nombre;
}
