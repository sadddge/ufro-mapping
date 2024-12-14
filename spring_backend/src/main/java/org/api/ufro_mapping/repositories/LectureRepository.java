package org.api.ufro_mapping.repositories;

import org.api.ufro_mapping.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
