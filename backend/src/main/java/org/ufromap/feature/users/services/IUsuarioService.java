package org.ufromap.feature.users.services;

import org.ufromap.core.base.ICrudService;
import org.ufromap.feature.users.dto.UsuarioRequestDTO;
import org.ufromap.feature.lectures.dto.HorarioClaseDTO;
import org.ufromap.feature.users.dto.UsuarioDTO;
import org.ufromap.feature.users.models.Usuario;

import java.util.List;

public interface IUsuarioService extends ICrudService<UsuarioDTO, UsuarioRequestDTO, Usuario> {
    List<HorarioClaseDTO> getHorarioByUsuarioId(int id);
}
