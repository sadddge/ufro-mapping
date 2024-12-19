package org.api.ufro_mapping.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureUpdateDTO {
    private Long classroomId;
    private Long courseId;
    private Integer dayOfWeek;
    private Integer period;
    private String teacher;
    private Integer module;
}
