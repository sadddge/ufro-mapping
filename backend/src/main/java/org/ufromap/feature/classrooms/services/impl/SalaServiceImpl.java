package org.ufromap.feature.classrooms.services.impl;

import org.ufromap.feature.classrooms.dto.SalaRequestDTO;
import org.ufromap.feature.buildings.dto.EdificioDTO;
import org.ufromap.feature.classrooms.dto.SalaDTO;
import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.EntityNotFoundException;
import org.ufromap.feature.buildings.models.Edificio;
import org.ufromap.feature.classrooms.models.Sala;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.repositories.SalaRepository;
import org.ufromap.feature.classrooms.services.ISalaService;

import java.util.List;
import java.util.Map;

public class SalaServiceImpl implements ISalaService {

    private final SalaRepository salaRepository;
    private final EdificioRepository edificioRepository;

    public SalaServiceImpl() {
        this.salaRepository = new SalaRepository();
        this.edificioRepository = new EdificioRepository();
    }


    @Override
    public List<SalaDTO> findAll() {
        return salaRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public SalaDTO findById(int id) throws EntityNotFoundException {
        Sala sala = salaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sala no encontrada con la id: " + id));
        return entityToDTO(sala);
    }

    @Override
    public List<SalaDTO> findByFilter(Map<String, Object> filters) throws EntityNotFoundException {
        return salaRepository.findByFilter(filters).stream().map(this::entityToDTO).toList();
    }

    @Override
    public SalaDTO add(SalaRequestDTO salaRequestDTO) throws BadRequestException {
        validateEntity(salaRequestDTO);
        Sala sala = dtoToEntity(0, salaRequestDTO);
        return entityToDTO(salaRepository.add(sala));
    }

    @Override
    public SalaDTO update(int id, SalaRequestDTO salaRequestDTO) throws EntityNotFoundException {
        Sala entity = salaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sala no encontrada con la id: " + id));
        SalaRequestDTO requestdDTO = SalaRequestDTO.builder()
                .nombre(salaRequestDTO.getNombre() != null ? salaRequestDTO.getNombre() : entity.getNombre())
                .edificioId(salaRequestDTO.getEdificioId() != -1 ? salaRequestDTO.getEdificioId() : entity.getEdificioId())
                .build();
        validateEntity(requestdDTO);
        Sala sala = dtoToEntity(id, requestdDTO);
        return entityToDTO(salaRepository.update(sala));
    }

    @Override
    public void delete(int id) {
        findById(id);
        salaRepository.delete(id);
    }

    @Override
    public SalaDTO entityToDTO(Sala sala) {
        Edificio edificio = edificioRepository.findById(sala.getEdificioId()).orElseThrow(() -> new EntityNotFoundException("Edificio no encontrado con la id: " + sala.getEdificioId()));
        EdificioDTO edificioDTO = EdificioDTO.builder()
                .id(edificio.getId())
                .nombre(edificio.getNombre())
                .alias(edificio.getAlias())
                .build();
        return SalaDTO.builder()
                .id(sala.getId())
                .nombre(sala.getNombre())
                .edificio(edificioDTO)
                .build();
    }

    @Override
    public Sala dtoToEntity(int id, SalaRequestDTO dto) {
        return new Sala(id,  dto.getEdificioId(), dto.getNombre());
    }

    @Override
    public void validateEntity(SalaRequestDTO sala) {
        if (sala.getNombre() == null || sala.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de la sala es obligatorio.");
        }
        if (edificioRepository.findById(sala.getEdificioId()).isEmpty()) {
            throw new BadRequestException("El edificio de la sala no existe.");
        }
    }

    @Override
    public List<SalaDTO> getSalasByEdificioId(int edificioId) throws EntityNotFoundException {
        return salaRepository.findByEdificioId(edificioId).stream().map(this::entityToDTO).toList();
    }
}
