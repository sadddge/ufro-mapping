package org.api.ufro_mapping.repositories;

import org.api.ufro_mapping.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
