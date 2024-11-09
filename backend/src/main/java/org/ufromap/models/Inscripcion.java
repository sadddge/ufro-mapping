package org.ufromap.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inscripcion {
    private int id;
    private int usuarioId;
    private int asignaturaId;

    public Inscripcion(int id, int usuarioId, int asignaturaId) {
        this.usuarioId = usuarioId;
        this.asignaturaId = asignaturaId;
    }
}
