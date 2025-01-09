package org.ufromap.feature.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionRequestDTO {
    private int usuarioId;
    private int asignaturaId;
}
