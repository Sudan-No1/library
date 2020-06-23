package com.sd.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Package: com.sd.model.BookInfo
 * @Description: 
 * @author sudan
 * @date 2020/5/27 18:01
 */
 
@Data
@Table(name = "book_info")
public class BookInfo  extends BaseModel{

    @Column(name = "book_no")
    private String bookNo;

    /**书名*/
    @Column(name = "name")
    private String name;

    /**价格*/
    @Column(name = "price")
    private BigDecimal price;

    /**类型*/
    @Column(name = "type")
    private String type;

    /**库存数*/
    @Column(name = "inventory")
    private Integer inventory;

    /**是否有效  1 有效 0 无效 若库存数量 */
    @Column(name = "active")
    private Integer active;

}