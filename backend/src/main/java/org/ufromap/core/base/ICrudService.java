package org.ufromap.core.base;

import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Map;

public interface ICrudService<DTO, DTORequest, Entity> {
    List<DTO> findAll();

    DTO findById(int id) throws EntityNotFoundException;

    List<DTO> findByFilter(Map<String, Object> filters) throws EntityNotFoundException;

    DTO add(DTORequest dtoRequest) throws BadRequestException;

    DTO update(int id, DTORequest dtoRequest) throws EntityNotFoundException;

    void delete(int id) throws EntityNotFoundException;
    DTO entityToDTO(Entity entity);
    Entity dtoToEntity(int id, DTORequest dto);
    void validateEntity(DTORequest dtoRequest) throws BadRequestException;
}
