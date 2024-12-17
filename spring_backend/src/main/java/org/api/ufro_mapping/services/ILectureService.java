package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.LectureRequestDTO;
import org.api.ufro_mapping.dto.request.update.LectureUpdateDTO;
import org.api.ufro_mapping.dto.response.LectureDTO;

import java.util.List;
import java.util.Optional;

public interface ILectureService {
    List<LectureDTO> findAll();
    Optional<LectureDTO> findById(Long id);
    LectureDTO save(LectureRequestDTO lectureDTO);
    Optional<LectureDTO> update(Long id, LectureUpdateDTO lectureDTO);
    boolean deleteById(Long id);
}
