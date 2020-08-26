package com.sd.dto.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @Package: com.sd.dto.student.StudentDto
 * @Description: 
 * @author sudan
 * @date 2020/8/19 10:09
 */

@Data
public class StudentDto {
    /**姓名*/
    private String name;
    /**班级*/
    private String classNo;
    /**邮箱*/
    private String email;

    /**登录账号*/
    @JsonIgnore
    private String loginName;
    /**登录密码*/
    @JsonIgnore
    private String password;
}