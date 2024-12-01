package org.ufromap.models;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inscripcion {
    private int id;
    private int usuarioId;
    private int asignaturaId;
}