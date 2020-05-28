package com.sd.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @Package: com.sd.dto.BookDto
 * @Description: 
 * @author sudan
 * @date 2020/5/28 10:49
 */
 
@Data
public class BookDto {
    /**图书编号*/
    private String bookNo;
    /**书名*/
    private String name;
    /**价格*/
    private BigDecimal price;
    /**类型*/
    private String type;
    /**库存数*/
    private Integer inventory;
    /**是否有效  1 有效 0 无效 若库存数量 */
    private Integer active;

}