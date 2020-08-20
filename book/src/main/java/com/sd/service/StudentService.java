package com.sd.service;

import com.sd.dto.Page;
import com.sd.dto.student.StudentDto;
import com.sd.dto.student.StudentQueryDto;
import com.sd.model.StudentInfo;

/**
 * @Package: com.sd.service.StudentService
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:33
 */
 
 
public interface StudentService {

    void add(StudentDto studentDto);

    void update(StudentInfo studentInfo);

    StudentInfo queryByStudentNo(String studentNo);

    Page<StudentInfo> queryPage(StudentQueryDto studentQueryDto);
}