package org.ufromap.feature.buildings.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Edificio {
    private int id;
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;
}