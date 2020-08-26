package com.sd.dto;

import com.sd.dto.customer.CustomerDto;
import com.sd.dto.student.StudentDto;
import com.sd.dto.teacher.TeacherDto;
import lombok.Data;

/**
 * @Package: com.sd.dto.UserDto
 * @Description: 
 * @author sudan
 * @date 2020/8/21 10:54
 */
 
@Data
public class UserDto {

    /**登录账号*/
    private String loginName;
    /**登录密码*/
    private String password;
    /**角色*/
    private String role;
    /**客户信息*/
    private CustomerDto customerDto;

    /**学生信息*/
    private StudentDto studentDto;

    /**老师信息*/
    private TeacherDto teacherDto;
}