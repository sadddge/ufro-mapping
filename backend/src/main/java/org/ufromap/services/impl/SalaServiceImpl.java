package org.ufromap.services.impl;

import org.json.JSONObject;
import org.ufromap.dto.request.SalaRequestDTO;
import org.ufromap.dto.response.ClaseDTO;
import org.ufromap.dto.response.EdificioDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.models.Sala;
import org.ufromap.repositories.ClaseRepository;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.repositories.SalaRepository;
import org.ufromap.services.ISalaService;

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
        validateEntity(salaRequestDTO);
        Sala sala = dtoToEntity(id, salaRequestDTO);
        return entityToDTO(salaRepository.update(sala));
    }

    @Override
    public SalaDTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException {
        Sala sala = salaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sala no encontrada con la id: " + id));
        SalaRequestDTO requestdDTO = SalaRequestDTO.builder()
                .nombre(jsonObject.optString("nombre", sala.getNombre()))
                .edificioId(jsonObject.optInt("edificioId", sala.getEdificioId()))
                .build();
        return update(id, requestdDTO);
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
