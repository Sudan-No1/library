package com.sd.service.people.impl;

import com.sd.common.constant.PeopleTypeEnum;
import com.sd.common.util.MD5Util;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.PeopleFactoryService;
import com.sd.service.people.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void login(LoginDto loginDto) {
        peopleFactoryService.createPeopleService(loginDto.getRole()).login(loginDto);
    }

    @Override
    public void addUser(UserDto userDto) {
        List<String> allCode = PeopleTypeEnum.getAllCode();
        for (String code: allCode){
            peopleFactoryService.createPeopleService(code).checkUserExist(userDto.getLoginName());
        }
        userDto.setPassword(MD5Util.encrypt(userDto.getPassword()));
        peopleFactoryService.createPeopleService(userDto.getRole()).addUser(userDto);
    }
}