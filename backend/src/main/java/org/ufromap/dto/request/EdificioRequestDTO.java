package org.ufromap.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdificioRequestDTO {
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;
}
