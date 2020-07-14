package com.sd.teaching.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Package: com.sd.teaching.model.BaseModel
 * @Description: 
 * @author sudan
 * @date 2020/7/14 10:08
 */
 
@Data
public class BaseModel {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    private String id;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "updator_id")
    private String updatorId;

    /**
     * 更新人
     */
    @Column(name = "is_deleted")
    private String isDeleted;
}