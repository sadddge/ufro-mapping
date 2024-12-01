package org.ufromap.services.impl;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.ufromap.dto.request.EdificioRequestDTO;
import org.ufromap.dto.response.EdificioDTO;
import org.ufromap.dto.response.LocationDTO;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.services.IEdificioService;


public class EdificioServiceImpl implements IEdificioService {

    private final EdificioRepository edificioRepository;

    public EdificioServiceImpl() {
        this.edificioRepository = new EdificioRepository();
    }

    @Override
    public List<EdificioDTO> findAll() {
        return edificioRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public EdificioDTO findById(int id) throws EntityNotFoundException {
        Edificio edificio = edificioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Edificio no encontrado con la id: " + id));
        return entityToDTO(edificio);
    }

    @Override
    public List<EdificioDTO> findByFilter(Map<String, Object> filters) throws EntityNotFoundException {
        return edificioRepository.findByFilter(filters).stream().map(this::entityToDTO).toList();
    }

    @Override
    public EdificioDTO add(EdificioRequestDTO edificioRequestDTO) throws BadRequestException {
        validateEntity(edificioRequestDTO);
        Edificio edificio = dtoToEntity(0, edificioRequestDTO);
        return entityToDTO(edificioRepository.add(edificio));
    }

    @Override
    public EdificioDTO update(int id, EdificioRequestDTO edificioRequestDTO) throws EntityNotFoundException {
        validateEntity(edificioRequestDTO);
        Edificio edificio = dtoToEntity(id, edificioRequestDTO);
        return entityToDTO(edificioRepository.update(edificio));
    }

    @Override
    public EdificioDTO patch(int id, JSONObject jsonObject) throws EntityNotFoundException {
        Edificio edificio = edificioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Edificio no encontrado con la id: " + id));
        EdificioRequestDTO requestdDTO = EdificioRequestDTO.builder()
                .nombre(jsonObject.optString("nombre", edificio.getNombre()))
                .alias(jsonObject.optString("alias", edificio.getAlias()))
                .tipo(jsonObject.optString("tipo", edificio.getTipo()))
                .latitud(jsonObject.optFloat("latitud", edificio.getLatitud()))
                .longitud(jsonObject.optFloat("longitud", edificio.getLongitud()))
                .build();
        return update(id, requestdDTO);
    }


    @Override
    public void delete(int id) {
        findById(id);
        edificioRepository.delete(id);
    }

    @Override
    public EdificioDTO entityToDTO(Edificio edificio) {
        return EdificioDTO.builder()
                .id(edificio.getId())
                .nombre(edificio.getNombre())
                .alias(edificio.getAlias())
                .tipo(edificio.getTipo())
                .latitud(edificio.getLatitud())
                .longitud(edificio.getLongitud())
                .build();
    }

    @Override
    public Edificio dtoToEntity(int id, EdificioRequestDTO dto) {
        return new Edificio(id, dto.getNombre(), dto.getAlias(), dto.getTipo(), dto.getLatitud(), dto.getLongitud());
    }

    @Override
    public void validateEntity(EdificioRequestDTO edificio) {
        if (edificio.getNombre() == null || edificio.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del edificio es obligatorio.");
        }
        if (edificio.getLatitud() == -1) {
            throw new BadRequestException("La latitud del edificio es obligatoria.");
        }
        if (edificio.getLongitud() == -1) {
            throw new BadRequestException("La longitud del edificio es obligatoria.");
        }
    }

    @Override
    public List<LocationDTO> findAllLocations() {
        return edificioRepository.findAll().stream().map(this::entityToLocationDTO).toList();
    }

    private LocationDTO entityToLocationDTO(Edificio edificio) {
        return LocationDTO.builder()
                .idEdificio(edificio.getId())
                .nombreEdificio(edificio.getNombre())
                .aliasEdificio(edificio.getAlias())
                .latitud(edificio.getLatitud())
                .longitud(edificio.getLongitud())
                .build();
    }
}
