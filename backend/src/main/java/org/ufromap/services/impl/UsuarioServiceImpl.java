package org.ufromap.services.impl;

import org.json.JSONObject;
import org.ufromap.auth.Validator;
import org.ufromap.dto.request.UsuarioRequestDTO;
import org.ufromap.dto.response.HorarioClaseDTO;
import org.ufromap.dto.response.UsuarioDTO;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Usuario;
import org.ufromap.repositories.UsuarioRepository;
import org.ufromap.services.IUsuarioService;

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
        validateEntity(usuarioRequestDTO);
        Usuario usuario = dtoToEntity(id, usuarioRequestDTO);
        return entityToDTO(usuarioRepository.update(usuario));
    }

    @Override
    public UsuarioDTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con la id: " + id));
        UsuarioRequestDTO requestdDTO = UsuarioRequestDTO.builder()
                .nombre(jsonObject.optString("nombre", usuario.getNombre()))
                .correo(jsonObject.optString("email", usuario.getCorreo()))
                .contrasenia(jsonObject.optString("password", usuario.getContrasenia()))
                .build();
        return update(id, requestdDTO);
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
