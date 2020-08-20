package com.sd.dto.teacher;

import lombok.Data;

import javax.persistence.Column;

/**
 * @Package: com.sd.dto.teacher.TeacherDto
 * @Description: 
 * @author sudan
 * @date 2020/8/20 16:09
 */
 
@Data
public class TeacherDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 教师号
     */
    private String teacherNo;

    /**
     * 教学科目
     */
    private String subject;

    /**
     * 代课班级
     */
    private String classNo;

}