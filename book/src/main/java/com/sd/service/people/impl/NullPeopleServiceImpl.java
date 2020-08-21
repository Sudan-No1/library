package com.sd.service.people.impl;

import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.OperationService;
import com.sd.service.people.PeopleService;
import org.springframework.stereotype.Service;

/**
 * @Package: com.sd.service.people.source.impl.NullPeopleService
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:34
 */
 
@Service
public class NullPeopleServiceImpl implements OperationService {
    @Override
    public String getCode() {
        return "null";
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return null;
    }
}