package com.sd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package: com.sd.model.BaseModel
 * @Description: 基础类
 * @author sudan
 * @date 2020/5/28 10:35
 */
 
@Data
public class BaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "updator_id")
    private Date updatorId;
}