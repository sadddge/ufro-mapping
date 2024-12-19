package org.api.ufro_mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureDTO {
    private Long id;
    private int dayOfWeek;
    private int period;
    private String teacher;
    private Integer module;
    private ClassroomDTO classroom;
    private CourseDTO course;
}
