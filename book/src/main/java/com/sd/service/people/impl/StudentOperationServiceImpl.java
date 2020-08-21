package com.sd.service.people.impl;

import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.OperationService;
import com.sd.service.people.PeopleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Package: StudentOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:40
 */

@Service
public class StudentOperationServiceImpl implements OperationService {

    @Value("${borrow.student.bookNum}")
    private Integer bookNum;

    @Value("${borrow.student.borrowDays}")
    private Integer borrowDays;

    @Override
    public String getCode() {
        return "student";
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return new BookBorrowDto(bookNum,borrowDays);
    }
}