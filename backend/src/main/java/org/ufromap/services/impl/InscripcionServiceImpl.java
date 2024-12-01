package org.ufromap.services.impl;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.ufromap.dto.request.InscripcionRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.dto.response.InscripcionDTO;
import org.ufromap.dto.response.UsuarioDTO;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Inscripcion;
import org.ufromap.models.Usuario;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.repositories.InscripcionRepository;
import org.ufromap.repositories.UsuarioRepository;
import org.ufromap.services.IInscripcionService;

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
        validateEntity(inscripcionRequestDTO);
        Inscripcion inscripcion = dtoToEntity(id, inscripcionRequestDTO);
        return entityToDTO(inscripcionRepository.update(inscripcion));
    }

    @Override
    public InscripcionDTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException {
        Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Inscripcion no encontrada con la id: " + id));
        InscripcionRequestDTO requestdDTO = InscripcionRequestDTO.builder()
                .asignaturaId(jsonObject.optInt("asignaturaId", inscripcion.getAsignaturaId()))
                .usuarioId(jsonObject.optInt("usuarioId", inscripcion.getUsuarioId()))
                .build();
        return update(id, requestdDTO);
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
