package com.sd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

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
    private BigDecimal createTime;

    @Column(name = "creator_id")
    private BigDecimal creatorId;

    @Column(name = "update_time")
    private BigDecimal updateTime;

    @Column(name = "updator_id")
    private BigDecimal updatorId;
}