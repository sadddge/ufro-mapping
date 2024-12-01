package org.ufromap.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaseRequestDTO {
    private int salaId;
    private int asignaturaId;
    private int diaSemana;
    private int periodo;
    private String docente;
    private int modulo;
}
