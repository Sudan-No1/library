package com.sd.service;

import com.sd.dto.Page;
import com.sd.dto.teacher.TeacherDto;
import com.sd.dto.teacher.TeacherQueryDto;

/**
 * @Package: com.sd.service.TeacherService
 * @Description: 
 * @author sudan
 * @date 2020/8/20 16:11
 */
 
 
public interface TeacherService {
    void add(TeacherDto teacherDto);

    void update(TeacherDto teacherDto);

    TeacherDto queryByStudentNo(String teacherNo);

    Page<TeacherDto> queryPage(TeacherQueryDto teacherQueryDto);
}