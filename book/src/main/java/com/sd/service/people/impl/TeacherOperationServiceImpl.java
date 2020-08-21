package com.sd.service.people.impl;

import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.OperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Package: TeacherOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:42
 */
 
@Service
public class TeacherOperationServiceImpl implements OperationService {

    @Value("${borrow.student.bookNum}")
    private Integer bookNum;

    @Value("${borrow.student.borrowDays}")
    private Integer borrowDays;

    @Override
    public String getCode() {
        return "teacher";
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return new BookBorrowDto(bookNum,borrowDays);
    }
}