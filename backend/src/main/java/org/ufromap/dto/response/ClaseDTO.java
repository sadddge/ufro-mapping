package org.ufromap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaseDTO {
    private int id;
    private SalaDTO sala;
    private AsignaturaDTO asignatura;
    private int diaSemana;
    private int periodo;
    private String docente;
    private int modulo;
}
