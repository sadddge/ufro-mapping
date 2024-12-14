package org.api.ufro_mapping.repositories;

import org.api.ufro_mapping.models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByBuildingId(Long buildingId);
}
