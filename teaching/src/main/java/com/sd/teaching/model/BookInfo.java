package com.sd.teaching.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(name = "book_info")
public class BookInfo extends BaseModel {

    /**
     * 图书编号
     */
    @Column(name = "book_no")
    private String bookNo;

    /**
     * 书名
     */
    @Column(name = "name")
    private String name;


    /**
     * 价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 图书分类
     */
    @Column(name = "type")
    private String type;

    /**
     * 库存
     */
    @Column(name = "inventory")
    private Integer inventory;

    /**
     * 是否有效 1 是 0 否 （当库存大于0时有效）
     */
    @Column(name = "active")
    private Integer active;
}