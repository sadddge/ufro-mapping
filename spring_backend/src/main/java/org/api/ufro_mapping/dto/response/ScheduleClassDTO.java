package org.api.ufro_mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleClassDTO {
    private Long id;
    private String courseName;
    private String classroomName;
    private String code;
    private int day;
    private int period;
    private int module;
}
