package org.ufromap.services;

import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICrudService<DTO, DTORequest, Entity> {
    List<DTO> findAll();

    DTO findById(int id) throws EntityNotFoundException;

    List<DTO> findByFilter(Map<String, Object> filters) throws EntityNotFoundException;

    DTO add(DTORequest dtoRequest) throws BadRequestException;

    DTO update(int id, DTORequest dtoRequest) throws EntityNotFoundException;

    DTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException;

    void delete(int id) throws EntityNotFoundException;
    DTO entityToDTO(Entity entity);
    Entity dtoToEntity(int id, DTORequest dto);
    void validateEntity(DTORequest dtoRequest) throws BadRequestException;
}
