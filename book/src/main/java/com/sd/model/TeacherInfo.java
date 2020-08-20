package com.sd.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "teacher_info")
@Data
public class TeacherInfo extends BaseModel {

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 学号
     */
    @Column(name = "teacher_no")
    private String teacherNo;

    /**
     * 教学科目
     */
    @Column(name = "subject")
    private String subject;

    /**
     * 代课班级
     */
    @Column(name = "class_no")
    private String classNo;
}