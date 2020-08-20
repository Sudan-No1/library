package com.sd.dto.customer;

import com.sd.dto.PageDto;
import lombok.Data;

/**
 * @Package: com.sd.dto.customer.CustomerQueryDto
 * @Description: 
 * @author sudan
 * @date 2020/8/20 17:45
 */
 
@Data
public class CustomerQueryDto extends PageDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String certificateNo;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

}