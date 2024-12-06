package org.ufromap.feature.buildings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private int idEdificio;
    private String nombreEdificio;
    private String aliasEdificio;
    private float latitud;
    private float longitud;
}
