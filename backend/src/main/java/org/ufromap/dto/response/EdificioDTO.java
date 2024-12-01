package org.ufromap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdificioDTO {
    private int id;
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;
}
