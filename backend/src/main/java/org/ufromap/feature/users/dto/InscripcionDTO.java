package org.ufromap.feature.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ufromap.feature.courses.dto.AsignaturaDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO {
    private int id;
    private UsuarioDTO usuario;
    private AsignaturaDTO asignaturaDTO;
}
