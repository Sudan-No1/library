package com.sd.dto;

import com.sd.common.annotation.Dict;
import com.sd.common.annotation.RepeatParam;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

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
    @Dict(type = "bookType")
    private String type;
    /**库存数*/
    private Integer inventory;


}