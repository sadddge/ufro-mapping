package org.ufromap.feature.lectures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioClaseDTO {
    private int idClase;
    private String nombreAsignatura;
    private String codigoAsignatura;
    private int modulo;
    private String nombreSala;
    private int diaSemana;
    private int periodo;
}