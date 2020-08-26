package com.sd.service.people.impl;

import com.sd.common.constant.PeopleTypeEnum;
import com.sd.common.exception.BusinessException;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.service.people.OperationService;

import javax.management.OperationsException;

/**
 * @Package: com.sd.service.people.impl.OperationAbstractServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 11:29
 */
 
 
public class OperationAbstractServiceImpl implements OperationService {
    @Override
    public PeopleTypeEnum getCode() {
        return null;
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return null;
    }

    @Override
    public void login(LoginDto loginDto) {
        throw new BusinessException("角色不存在");
    }

    @Override
    public void addUser(UserDto userDto) {
        throw new BusinessException("角色不存在");
    }

    @Override
    public void checkUserExist(String loginName) {
        throw new BusinessException("角色不存在");
    }
}