package com.sd.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Package: com.sd.model.StudentInfo
 * @Description: 
 * @author sudan
 * @date 2020/5/28 15:51
 */

@Data
@Table(name = "student_info")
public class StudentInfo  extends BaseModel{

    /**姓名*/
    @Column(name = "name")
    private String name;
    /**班级*/
    @Column(name = "class_no")
    private String classNo;
    /**邮箱*/
    @Column(name = "email")
    private String email;


}