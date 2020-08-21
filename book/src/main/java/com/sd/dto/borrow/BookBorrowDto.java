package com.sd.dto.borrow;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Package: com.sd.dto.borrow.BookBorrowDto
 * @Description: 
 * @author sudan
 * @date 2020/8/21 10:06
 */
 
@Data
@AllArgsConstructor
public class BookBorrowDto {

    private Integer bookNum;

    private Integer borrowDays;

}