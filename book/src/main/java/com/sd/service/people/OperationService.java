package com.sd.service.people;

import com.sd.common.constant.PeopleTypeEnum;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;

/**
 * @Package: OperationService
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:43
 */
 
 
public interface OperationService {

    PeopleTypeEnum getCode();

    BookBorrowDto getBorrowInfo();

    void login(LoginDto loginDto);

    void addUser(UserDto userDto);

    void checkUserExist(String loginName);
}