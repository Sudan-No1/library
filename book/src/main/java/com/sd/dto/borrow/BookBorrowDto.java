package com.sd.dto.borrow;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Package: com.sd.dto.borrow.BookBorrowDto
 * @Description: 
 * @author sudan
 * @date 2020/8/21 10:06
 */
 
@Data
@AllArgsConstructor
public class BookBorrowDto {

    /**最大借书数量*/
    private Integer bookNum;
    /**最大借书天数*/
    private Integer borrowDays;
    /**逾期罚款金额*/
    private BigDecimal fine;

}