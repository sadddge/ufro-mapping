package org.ufromap.models;

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