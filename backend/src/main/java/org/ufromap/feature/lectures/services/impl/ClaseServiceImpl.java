package org.ufromap.feature.lectures.services.impl;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.ufromap.feature.lectures.dto.ClaseRequestDTO;
import org.ufromap.feature.courses.dto.AsignaturaDTO;
import org.ufromap.feature.lectures.dto.ClaseDTO;
import org.ufromap.feature.buildings.dto.EdificioDTO;
import org.ufromap.feature.classrooms.dto.SalaDTO;
import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.EntityNotFoundException;
import org.ufromap.feature.courses.models.Asignatura;
import org.ufromap.feature.lectures.models.Clase;
import org.ufromap.feature.buildings.models.Edificio;
import org.ufromap.feature.classrooms.models.Sala;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.repositories.ClaseRepository;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.repositories.SalaRepository;
import org.ufromap.feature.lectures.services.IClaseService;


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
        Clase entity = claseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Clase no encontrada con la id: " + id));
        ClaseRequestDTO requestDTO = ClaseRequestDTO.builder()
                .salaId(clase.getSalaId() != -1 ? clase.getSalaId() : entity.getSalaId())
                .asignaturaId(clase.getAsignaturaId() != -1 ? clase.getAsignaturaId() : entity.getAsignaturaId())
                .diaSemana(clase.getDiaSemana() != -1 ? clase.getDiaSemana() : entity.getDiaSemana())
                .periodo(clase.getPeriodo() != -1 ? clase.getPeriodo() : entity.getPeriodo())
                .docente(clase.getDocente() != null ? clase.getDocente() : entity.getDocente())
                .modulo(clase.getModulo() != -1 ? clase.getModulo() : entity.getModulo())
                .build();
        validateEntity(requestDTO);
        Clase claseEntity = dtoToEntity(id, requestDTO);
        return entityToDTO(claseRepository.update(claseEntity));
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