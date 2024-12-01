package org.ufromap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO {
    private int id;
    private UsuarioDTO usuario;
    private AsignaturaDTO asignaturaDTO;
}
