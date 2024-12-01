package org.ufromap.services.impl;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.ufromap.dto.request.ClaseRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.dto.response.ClaseDTO;
import org.ufromap.dto.response.EdificioDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Clase;
import org.ufromap.models.Edificio;
import org.ufromap.models.Sala;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.repositories.ClaseRepository;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.repositories.SalaRepository;
import org.ufromap.services.IClaseService;


@AllArgsConstructor
public class ClaseServiceImpl implements IClaseService {
    private final ClaseRepository claseRepository;
    private final SalaRepository salaRepository;
    private final EdificioRepository edificioRepository;
    private final AsignaturaRepository asignaturaRepository;

    public ClaseServiceImpl() {
        this.claseRepository = new ClaseRepository();
        this.salaRepository = new SalaRepository();
        this.edificioRepository = new EdificioRepository();
        this.asignaturaRepository = new AsignaturaRepository();
    }
    @Override
    public List<ClaseDTO> findAll() {
        return claseRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public ClaseDTO findById(int id) {
        Clase clase = claseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Clase no encontrada con la id: " + id));
        return entityToDTO(clase);
    }

    @Override
    public List<ClaseDTO> findByFilter(Map<String, Object> filters) {
        return claseRepository.findByFilter(filters).stream().map(this::entityToDTO).toList();
    }

    @Override
    public ClaseDTO add(ClaseRequestDTO clase) {
        validateEntity(clase);
        Clase claseEntity = dtoToEntity(0, clase);
        return entityToDTO(claseRepository.add(claseEntity));
    }

    @Override
    public ClaseDTO update(int id, ClaseRequestDTO clase) {
        validateEntity(clase);
        Clase claseEntity = dtoToEntity(id, clase);
        return entityToDTO(claseRepository.update(claseEntity));
    }

    @Override
    public ClaseDTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException {
        Clase clase = claseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Clase no encontrada con la id: " + id));
        ClaseRequestDTO requestDTO = ClaseRequestDTO.builder()
                .salaId(jsonObject.optInt("salaId", clase.getSalaId()))
                .asignaturaId(jsonObject.optInt("asignaturaId", clase.getAsignaturaId()))
                .diaSemana(jsonObject.optInt("diaSemana", clase.getDiaSemana()))
                .periodo(jsonObject.optInt("periodo", clase.getPeriodo()))
                .docente(jsonObject.optString("docente", clase.getDocente()))
                .modulo(jsonObject.optInt("modulo", clase.getModulo()))
                .build();
        return update(id, requestDTO);
    }

    @Override
    public void delete(int id) {
        findById(id);
        claseRepository.delete(id);
    }

    @Override
    public ClaseDTO entityToDTO(Clase entity) {
        Sala sala = salaRepository.findById(entity.getSalaId()).orElseThrow(() -> new EntityNotFoundException("Sala no encontrada con la id: " + entity.getSalaId()));
        Edificio edificio = edificioRepository.findById(sala.getEdificioId()).orElseThrow(() -> new EntityNotFoundException("Edificio no encontrado con la id: " + sala.getEdificioId()));
        Asignatura asignatura = asignaturaRepository.findById(entity.getAsignaturaId()).orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con la id: " + entity.getAsignaturaId()));
        EdificioDTO edificioDTO = EdificioDTO.builder()
                .id(edificio.getId())
                .nombre(edificio.getNombre())
                .alias(edificio.getAlias())
                .build();
        SalaDTO salaDTO = SalaDTO.builder()
                .id(sala.getId())
                .nombre(sala.getNombre())
                .edificio(edificioDTO)
                .build();
        AsignaturaDTO asignaturaDTO = AsignaturaDTO.builder()
                .id(asignatura.getId())
                .nombre(asignatura.getNombre())
                .codigo(asignatura.getCodigo())
                .build();
        return ClaseDTO.builder()
                .id(entity.getId())
                .sala(salaDTO)
                .asignatura(asignaturaDTO)
                .diaSemana(entity.getDiaSemana())
                .periodo(entity.getPeriodo())
                .docente(entity.getDocente())
                .modulo(entity.getModulo())
                .build();
    }

    @Override
    public Clase dtoToEntity(int id, ClaseRequestDTO dto) {
        return new Clase(id, dto.getSalaId(), dto.getAsignaturaId(), dto.getDiaSemana(), dto.getPeriodo(), dto.getDocente(), dto.getModulo());
    }

    @Override
    public void validateEntity(ClaseRequestDTO clase) {
        if (clase.getDiaSemana() < 1 || clase.getDiaSemana() > 7) {
            throw new BadRequestException("El día de la semana debe ser un número entre 1 y 7.");
        }
        if (clase.getPeriodo() < 1 || clase.getPeriodo() > 11) {
            throw new BadRequestException("El periodo debe ser un número entre 1 y 11.");
        }
        if (clase.getModulo() < 1) {
            throw new BadRequestException("El módulo no puede ser menor a 1");
        }

    }
}