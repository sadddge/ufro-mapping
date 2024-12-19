package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.response.ScheduleClassDTO;

import java.util.List;

public interface IScheduleService {
    List<ScheduleClassDTO> getScheduleByUserId(Long userId);
    List<ScheduleClassDTO> getScheduleByClassroomId(Long classroomId);
    List<ScheduleClassDTO> getScheduleByCourseId(Long courseId);
}
