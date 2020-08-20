package com.sd.dto.student;

import com.sd.dto.PageDto;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Package: com.sd.dto.student.StudentQueryDto
 * @Description: 
 * @author sudan
 * @date 2020/8/19 9:46
 */
 
@Data
public class StudentQueryDto extends PageDto {
    /**学号*/
    private String studentNo;
    /**姓名*/
    private String name;
    /**班级*/
    private String classNo;
    /**邮箱*/
    private String email;
}