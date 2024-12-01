package org.ufromap.services;

import org.ufromap.dto.request.UsuarioRequestDTO;
import org.ufromap.dto.response.HorarioClaseDTO;
import org.ufromap.dto.response.UsuarioDTO;
import org.ufromap.models.Usuario;

import java.util.List;

public interface IUsuarioService extends ICrudService<UsuarioDTO, UsuarioRequestDTO, Usuario> {
    List<HorarioClaseDTO> getHorarioByUsuarioId(int id);
}
