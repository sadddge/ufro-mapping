package org.ufromap.feature.users.services.impl;

import lombok.AllArgsConstructor;
import org.ufromap.feature.users.dto.InscripcionRequestDTO;
import org.ufromap.feature.courses.dto.AsignaturaDTO;
import org.ufromap.feature.users.dto.InscripcionDTO;
import org.ufromap.feature.users.dto.UsuarioDTO;
import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.EntityNotFoundException;
import org.ufromap.feature.courses.models.Asignatura;
import org.ufromap.feature.users.models.Inscripcion;
import org.ufromap.feature.users.models.Usuario;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.repositories.InscripcionRepository;
import org.ufromap.repositories.UsuarioRepository;
import org.ufromap.feature.users.services.IInscripcionService;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
public class InscripcionServiceImpl implements IInscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final UsuarioRepository usuarioRepository;
    public InscripcionServiceImpl() {
        this.inscripcionRepository = new InscripcionRepository();
        this.asignaturaRepository = new AsignaturaRepository();
        this.usuarioRepository = new UsuarioRepository();
    }

    @Override
    public List<InscripcionDTO> findAll() {
        return inscripcionRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public InscripcionDTO findById(int id) throws EntityNotFoundException {
        Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Inscripcion no encontrada con la id: " + id));
        return entityToDTO(inscripcion);
    }

    @Override
    public List<InscripcionDTO> findByFilter(Map<String, Object> filters) throws EntityNotFoundException {
        return inscripcionRepository.findByFilter(filters).stream().map(this::entityToDTO).toList();
    }

    @Override
    public InscripcionDTO add(InscripcionRequestDTO inscripcionRequestDTO) throws BadRequestException {
        validateEntity(inscripcionRequestDTO);
        Inscripcion inscripcion = dtoToEntity(0, inscripcionRequestDTO);
        return entityToDTO(inscripcionRepository.add(inscripcion));
    }

    @Override
    public InscripcionDTO update(int id, InscripcionRequestDTO inscripcionRequestDTO) throws EntityNotFoundException {
        Inscripcion entity = inscripcionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Inscripcion no encontrada con la id: " + id));
        InscripcionRequestDTO requestdDTO = InscripcionRequestDTO.builder()
                .asignaturaId(inscripcionRequestDTO.getAsignaturaId() != -1 ? inscripcionRequestDTO.getAsignaturaId() : entity.getAsignaturaId())
                .usuarioId(inscripcionRequestDTO.getUsuarioId() != -1 ? inscripcionRequestDTO.getUsuarioId() : entity.getUsuarioId())
                .build();
        validateEntity(requestdDTO);
        Inscripcion inscripcion = dtoToEntity(id, requestdDTO);
        return entityToDTO(inscripcionRepository.update(inscripcion));
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        findById(id);
        inscripcionRepository.delete(id);
    }

    @Override
    public InscripcionDTO entityToDTO(Inscripcion inscripcion) {
        Asignatura asignatura = asignaturaRepository.findById(inscripcion.getAsignaturaId()).orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con la id: " + inscripcion.getAsignaturaId()));
        Usuario usuario = usuarioRepository.findById(inscripcion.getUsuarioId()).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con la id: " + inscripcion.getUsuarioId()));
        AsignaturaDTO asignaturaDTO = AsignaturaDTO.builder()
                .id(asignatura.getId())
                .nombre(asignatura.getNombre())
                .build();
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .build();
        return InscripcionDTO.builder()
                .id(inscripcion.getId())
                .usuario(usuarioDTO)
                .asignaturaDTO(asignaturaDTO)
                .build();
    }

    @Override
    public Inscripcion dtoToEntity(int id, InscripcionRequestDTO dto) {
        return new Inscripcion(id, dto.getUsuarioId(), dto.getAsignaturaId());
    }

    @Override
    public void validateEntity(InscripcionRequestDTO inscripcionRequestDTO) throws BadRequestException {
        if (inscripcionRequestDTO.getUsuarioId() == 0 || inscripcionRequestDTO.getAsignaturaId() == 0) {
            throw new BadRequestException("UsuarioId y AsignaturaId son requeridos");
        }
    }

    @Override
    public List<AsignaturaDTO> getAsignaturasByUsuarioId(int id) {
        return inscripcionRepository.findAsignaturasByUsuarioId(id)
                .stream()
                .map(asignatura -> AsignaturaDTO.builder()
                        .id(asignatura.getId())
                        .nombre(asignatura.getNombre())
                        .codigo(asignatura.getCodigo())
                        .sct(asignatura.getSct())
                        .build())
                .toList();
    }

    @Override
    public void deleteByUsuarioIdAndAsignaturaId(int usuarioId, int asignaturaId) {
        if (!inscripcionRepository.existsByUsuarioIdAndAsignaturaId(usuarioId, asignaturaId)) {
            throw new EntityNotFoundException("Inscripcion no encontrada con el usuarioId: " + usuarioId + " y asignaturaId: " + asignaturaId);
        }
        inscripcionRepository.deleteByUsuarioIdAndAsignaturaId(usuarioId, asignaturaId);
    }
}
