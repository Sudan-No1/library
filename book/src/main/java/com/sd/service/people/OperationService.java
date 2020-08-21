package com.sd.service.people;

import com.sd.dto.borrow.BookBorrowDto;

/**
 * @Package: OperationService
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:43
 */
 
 
public interface OperationService {

    String getCode();

    BookBorrowDto getBorrowInfo();
}