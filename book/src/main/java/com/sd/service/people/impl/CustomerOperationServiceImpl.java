package com.sd.service.people.impl;


import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.OperationService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Package: CustomerOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:39
 */
 
@Service
public class CustomerOperationServiceImpl implements OperationService {

    @Value("${borrow.customer.bookNum}")
    private Integer bookNum;

    @Value("${borrow.customer.borrowDays}")
    private Integer borrowDays;


    @Override
    public String getCode() {
        return "customer";
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return new BookBorrowDto(bookNum,borrowDays);
    }
}