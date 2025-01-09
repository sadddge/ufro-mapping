package org.ufromap.feature.lectures.services.impl;

import java.time.LocalDateTime;
import java.util.*;

import lombok.AllArgsConstructor;
import org.ufromap.feature.buildings.dto.LocationDTO;
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
    @Override
    public List<LocationDTO> getNextLocations(int id) {
        List<Clase> clases = claseRepository.getClasesByUserId(id);
        LocalDateTime now = LocalDateTime.now();
        int currentDay = now.getDayOfWeek().getValue();
        int currentPeriod = determinePeriod(now.getHour(), now.getMinute());

        Set<Integer> asignaturas = new HashSet<>();
        List<Clase> nextClasesList = clases.stream()
                .filter(clase -> (clase.getDiaSemana() == currentDay) && (clase.getPeriodo() > currentPeriod) && asignaturas.add(clase.getAsignaturaId()))
                .sorted(Comparator.comparing(Clase::getPeriodo))
                .toList();

        List<LocationDTO> locationsList = new ArrayList<>();

        for (Clase clase : nextClasesList) {
            Edificio edificio = edificioRepository.findBySalaId(clase.getSalaId());
            locationsList.add(LocationDTO.builder()
                    .idEdificio(edificio.getId())
                    .nombreEdificio(edificio.getNombre())
                    .aliasEdificio(edificio.getAlias())
                    .latitud(edificio.getLatitud())
                    .longitud(edificio.getLongitud())
                    .build());
        }
        return locationsList.subList(0, Math.min(locationsList.size(), 2));
    }

    public int determinePeriod (int hour, int minute) {
        int[][] periodos = {
                {8, 30, 9, 30},
                {9, 40, 10, 40},
                {10, 50, 11, 50},
                {12, 0, 13, 0},
                {14, 30, 15, 30},
                {15, 40, 16, 40},
                {16, 50, 17, 50},
                {18, 0, 19, 0},
                {19, 10, 20, 10},
                {20, 20, 21, 20},
        };
        for (int i = 0; i < periodos.length; i++) {
            int[] periodo = periodos[i];
            if ((hour > periodo[0] || (hour == periodo[0] && minute >= periodo[1])) && (hour < periodo[2] || (hour == periodo[2] && minute <= periodo[3]))) {
                return i;
            }
        }
        return 0;
    }
    @Override
    public boolean isInCurrentPeriod(int id) {
        List<Clase> clases = claseRepository.getClasesByUserId(id);
        LocalDateTime now = LocalDateTime.now();
        int currentDay = now.getDayOfWeek().getValue();
        int currentPeriod = determinePeriod(now.getHour(), now.getMinute());

        return clases.stream()
                .anyMatch(clase -> clase.getDiaSemana() == currentDay && clase.getPeriodo() == currentPeriod);
    }
}