package org.ufromap.feature.lectures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ufromap.feature.courses.dto.AsignaturaDTO;
import org.ufromap.feature.classrooms.dto.SalaDTO;

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
