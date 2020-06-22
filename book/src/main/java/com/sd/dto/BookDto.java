package com.sd.dto;

import com.sd.annotation.RepeatParam;
import com.sd.annotation.RepeatSubmit;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Package: com.sd.dto.BookDto
 * @Description: 
 * @author sudan
 * @date 2020/5/28 10:49
 */
 
@Data
public class BookDto {
    /**书名*/
    @NotBlank(message = "书名不能为空")
    @RepeatParam
    private String name;
    /**价格*/
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    /**类型*/
    @NotBlank(message = "类型不能为空")
    private String type;
    /**库存数*/
    private Integer inventory;
    /**是否有效  1 有效 0 无效 若库存数量 */
    private Integer active;

}