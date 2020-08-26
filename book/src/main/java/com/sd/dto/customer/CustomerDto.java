package com.sd.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CustomerDto{

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


    /**登录账号*/
    @JsonIgnore
    private String loginName;
    /**登录密码*/
    @JsonIgnore
    private String password;
   
}