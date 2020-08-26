package com.sd.service.people.impl;


import com.sd.common.constant.PeopleTypeEnum;
import com.sd.common.exception.BusinessException;
import com.sd.common.util.BeanMapper;
import com.sd.common.util.MD5Util;
import com.sd.dto.BaseContextHandler;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.dto.customer.CustomerDto;
import com.sd.model.CustomerInfo;
import com.sd.model.StudentInfo;
import com.sd.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Package: CustomerOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:39
 */
 
@Service
public class CustomerOperationServiceImpl extends OperationAbstractServiceImpl {

    @Autowired
    private CustomerService customerService;

    @Value("${borrow.customer.bookNum}")
    private Integer bookNum;

    @Value("${borrow.customer.borrowDays}")
    private Integer borrowDays;

    @Value("${borrow.customer.fine}")
    private BigDecimal fine;


    @Override
    public PeopleTypeEnum getCode() {
        return PeopleTypeEnum.PEOPLE_TYPE_ENUM_CUSTOMER;
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return new BookBorrowDto(bookNum,borrowDays,fine);
    }

    @Override
    public void login(LoginDto loginDto) {
        CustomerInfo customerInfo = customerService.queryByLoginName(loginDto.getLoginName());
        if(customerInfo == null){
            throw new BusinessException("用户不存在");
        }
        String password = customerInfo.getPassword();
        if(password.equals(MD5Util.encrypt(loginDto.getPassword()))){
            BaseContextHandler.setUser(loginDto);
        }else{
            throw new BusinessException("密码不正确");
        }
    }

    @Override
    public void addUser(UserDto userDto) {
        CustomerDto customerDto = userDto.getCustomerDto();
        customerDto.setLoginName(userDto.getLoginName());
        customerDto.setPassword(userDto.getPassword());
        customerService.add(customerDto);
    }

    @Override
    public void checkUserExist(String loginName) {
        CustomerInfo customerInfo = customerService.queryByLoginName(loginName);
        if(customerInfo != null) {
            throw new BusinessException("登录名已存在，请换一个名字");
        }
    }
}