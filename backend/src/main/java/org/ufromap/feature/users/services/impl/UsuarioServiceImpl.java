package org.ufromap.feature.users.services.impl;

import org.ufromap.core.utils.Validator;
import org.ufromap.feature.users.dto.UsuarioRequestDTO;
import org.ufromap.feature.lectures.dto.HorarioClaseDTO;
import org.ufromap.feature.users.dto.UsuarioDTO;
import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.EntityNotFoundException;
import org.ufromap.feature.users.models.Usuario;
import org.ufromap.repositories.UsuarioRepository;
import org.ufromap.feature.users.services.IUsuarioService;

import java.util.List;
import java.util.Map;

public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    public UsuarioServiceImpl() {
        this.usuarioRepository = new UsuarioRepository();
    }


    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public UsuarioDTO findById(int id) throws EntityNotFoundException {
        return usuarioRepository.findById(id).map(this::entityToDTO).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con la id: " + id));
    }

    @Override
    public List<UsuarioDTO> findByFilter(Map<String, Object> filters) throws EntityNotFoundException {
        return usuarioRepository.findByFilter(filters).stream().map(this::entityToDTO).toList();
    }

    @Override
    public UsuarioDTO add(UsuarioRequestDTO usuarioRequestDTO) throws BadRequestException {
        validateEntity(usuarioRequestDTO);
        Usuario usuario = dtoToEntity(0, usuarioRequestDTO);
        return entityToDTO(usuarioRepository.add(usuario));
    }

    @Override
    public UsuarioDTO update(int id, UsuarioRequestDTO usuarioRequestDTO) throws EntityNotFoundException {
        Usuario entity = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con la id: " + id));
        UsuarioRequestDTO requestdDTO = UsuarioRequestDTO.builder()
                .nombre(usuarioRequestDTO.getNombre() != null ? usuarioRequestDTO.getNombre() : entity.getNombre())
                .correo(usuarioRequestDTO.getCorreo() != null ? usuarioRequestDTO.getCorreo() : entity.getCorreo())
                .contrasenia(usuarioRequestDTO.getContrasenia() != null ? usuarioRequestDTO.getContrasenia() : entity.getContrasenia())
                .build();
        validateEntity(requestdDTO);
        Usuario usuario = dtoToEntity(id, requestdDTO);
        return entityToDTO(usuarioRepository.update(usuario));
    }

    @Override
    public void delete(int id) {
        findById(id);
        usuarioRepository.delete(id);
    }

    @Override
    public UsuarioDTO entityToDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .rol(usuario.getRol())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .build();
    }

    @Override
    public Usuario dtoToEntity(int id, UsuarioRequestDTO dto) {
        return new Usuario(id, dto.getRol(), dto.getNombre(), dto.getCorreo(), dto.getContrasenia());
    }

    @Override
    public void validateEntity(UsuarioRequestDTO usuarioRequestDTO) throws BadRequestException {
        Validator.validateEmail(usuarioRequestDTO.getCorreo());
        Validator.validatePassword(usuarioRequestDTO.getContrasenia());
    }

    @Override
    public List<HorarioClaseDTO> getHorarioByUsuarioId(int id) {
        return null;
    }
}
