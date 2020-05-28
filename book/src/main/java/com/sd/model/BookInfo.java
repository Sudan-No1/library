package com.sd.model;

import lombok.Data;

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
public class BookInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator="Mysql")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;
}