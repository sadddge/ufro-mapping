package org.api.ufro_mapping.repositories;

import org.api.ufro_mapping.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
