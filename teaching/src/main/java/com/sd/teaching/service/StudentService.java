package com.sd.teaching.service;

import com.sd.teaching.model.StudentInfo;

import java.util.List;

/**
 * @Package: com.sd.teaching.service.StudentService
 * @Description: 
 * @author sudan
 * @date 2020/7/14 14:26
 */
 
 
public interface StudentService {
    void add(StudentInfo studentInfo);

    void update(StudentInfo studentInfo);

    List<StudentInfo> queryAll();

    void delete(String stuNo);
}