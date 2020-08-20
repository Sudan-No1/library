package com.sd.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "customer_info")
public class CustomerInfo extends BaseModel {

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 身份证号
     */
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

}