package com.sd.teaching.model;

import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(name = "student_info")
public class StudentInfo extends BaseModel {

    /**
     * 学号
     */
    @Column(name = "stu_no")
    private String stuNo;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 班级号
     */
    @Column(name = "class_no")
    private String classNo;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

}