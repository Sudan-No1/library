package com.sd.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Package: com.sd.model.BookNoConfig
 * @Description: 
 * @author sudan
 * @date 2020/6/23 11:05
 */
 
@Data
@Table(name = "book_no_config")
public class BookNoConfig extends BaseModel{

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "version")
    private Integer version;

}