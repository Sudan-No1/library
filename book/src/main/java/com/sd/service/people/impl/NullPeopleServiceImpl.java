package com.sd.service.people.impl;

import com.sd.common.constant.PeopleTypeEnum;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
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
    public PeopleTypeEnum getCode() {
        return PeopleTypeEnum.PEOPLE_TYPE_ENUM_NULL;
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return null;
    }

    @Override
    public void login(LoginDto loginDto) {

    }

    @Override
    public void addUser(UserDto userDto) {

    }

    @Override
    public void checkUserExist(String loginName) {

    }
}