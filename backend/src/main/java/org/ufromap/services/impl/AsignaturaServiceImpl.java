package org.ufromap.services.impl;


import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.ufromap.dto.request.AsignaturaRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.services.IAsignaturaService;

public class AsignaturaServiceImpl implements IAsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    public AsignaturaServiceImpl() {
        this.asignaturaRepository = new AsignaturaRepository();
    }

    @Override
    public List<AsignaturaDTO> findAll() {
        return asignaturaRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public AsignaturaDTO findById(int id) throws EntityNotFoundException {
        Asignatura asignatura = asignaturaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con la id: " + id));
        return entityToDTO(asignatura);
    }

    @Override
    public List<AsignaturaDTO> findByFilter(Map<String, Object> filter) throws EntityNotFoundException {
        return asignaturaRepository.findByFilter(filter).stream().map(this::entityToDTO).toList();
    }

    @Override
    public AsignaturaDTO add(AsignaturaRequestDTO asignatura) throws BadRequestException {
        validateEntity(asignatura);
        Asignatura asignaturaEntity = dtoToEntity(0, asignatura);
        return entityToDTO(asignaturaRepository.add(asignaturaEntity));
    }

    @Override
    public AsignaturaDTO update(int id, AsignaturaRequestDTO asignatura) {
        validateEntity(asignatura);
        Asignatura asignaturaEntity = dtoToEntity(id, asignatura);
        return entityToDTO(asignaturaRepository.update(asignaturaEntity));
    }

    @Override
    public AsignaturaDTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException {
        Asignatura asignatura = asignaturaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con la id: " + id));
        AsignaturaRequestDTO requestdDTO = AsignaturaRequestDTO.builder()
                .nombre(jsonObject.optString("nombre", asignatura.getNombre()))
                .codigo(jsonObject.optString("codigo", asignatura.getCodigo()))
                .descripcion(jsonObject.optString("descripcion", asignatura.getDescripcion()))
                .sct(jsonObject.optInt("sct", asignatura.getSct()))
                .build();
        return update(id, requestdDTO);
    }


    @Override
    public void delete(int id) throws EntityNotFoundException {
        findById(id);
        asignaturaRepository.delete(id);
    }

    @Override
    public AsignaturaDTO entityToDTO(Asignatura entity) {
        return AsignaturaDTO
                .builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .codigo(entity.getCodigo())
                .descripcion(entity.getDescripcion())
                .sct(entity.getSct())
                .build();
    }

    @Override
    public Asignatura dtoToEntity(int id, AsignaturaRequestDTO dto) {
        return new Asignatura(id, dto.getNombre(), dto.getCodigo(), dto.getDescripcion(), dto.getSct());
    }

    @Override
    public void validateEntity(AsignaturaRequestDTO asignatura) {
        if (asignatura.getNombre() == null || asignatura.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de la asignatura es obligatorio.");
        }
        if (asignatura.getCodigo() == null || asignatura.getCodigo().isEmpty()) {
            throw new BadRequestException("El codigo de la asignatura es obligatorio.");
        }
    }
}