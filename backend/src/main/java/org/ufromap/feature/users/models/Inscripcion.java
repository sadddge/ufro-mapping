package org.ufromap.feature.users.models;

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