package org.api.ufro_mapping.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureRequestDTO {
    @NotNull(message = "Classroom id is mandatory")
    @Positive(message = "Classroom id must be positive")
    private Long classroomId;
    @NotNull(message = "Course id is mandatory")
    @Positive(message = "Course id must be positive")
    private Long courseId;
    @NotNull(message = "Day of week is mandatory")
    @Range(min = 1, max = 7, message = "Day of week must be between 1 and 7")
    private Integer dayOfWeek;
    @NotNull(message = "Period is mandatory")
    @Range(min = 1, max = 6, message = "Period must be between 1 and 6")
    private Integer period;
    private String teacher;
    private Integer module;
}
