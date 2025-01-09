package org.ufromap.feature.classrooms.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    private int id;
    private int edificioId;
    private String nombre;
}