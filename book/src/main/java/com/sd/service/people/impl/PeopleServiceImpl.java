package com.sd.service.people.impl;

import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.PeopleFactoryService;
import com.sd.service.people.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.sd.service.people.impl.PeopleServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/20 18:08
 */
 
 @Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleFactoryService peopleFactoryService;


    @Override
    public BookBorrowDto getBorrowInfo(String code) {
        return  peopleFactoryService.createPeopleService(code).getBorrowInfo();
    }
}