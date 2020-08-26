package com.sd.service.people;

import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;

/**
 * @Package: com.sd.service.people.PeopleService
 * @Description: 
 * @author sudan
 * @date 2020/8/20 18:08
 */

public interface PeopleService {

    BookBorrowDto getBorrowInfo(String code);

    void login(LoginDto loginDto);

    void addUser(UserDto userDto);
}