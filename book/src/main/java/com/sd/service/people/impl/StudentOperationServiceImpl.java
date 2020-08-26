package com.sd.service.people.impl;

import com.sd.common.constant.PeopleTypeEnum;
import com.sd.common.exception.BusinessException;
import com.sd.common.util.MD5Util;
import com.sd.dto.BaseContextHandler;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.dto.student.StudentDto;
import com.sd.model.StudentInfo;
import com.sd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Package: StudentOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:40
 */

@Service
public class StudentOperationServiceImpl extends OperationAbstractServiceImpl {

    @Autowired
    private StudentService studentService;

    @Value("${borrow.student.bookNum}")
    private Integer bookNum;

    @Value("${borrow.student.borrowDays}")
    private Integer borrowDays;


    @Value("${borrow.student.fine}")
    private BigDecimal fine;

    @Override
    public PeopleTypeEnum getCode() {
        return PeopleTypeEnum.PEOPLE_TYPE_ENUM_STUDENT;
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return new BookBorrowDto(bookNum,borrowDays,fine);
    }

    @Override
    public void login(LoginDto loginDto) {
        StudentInfo studentInfo = studentService.queryByLoginName(loginDto.getLoginName());
        if(studentInfo == null){
            throw new BusinessException("用户不存在");
        }
        String password = studentInfo.getPassword();
        if(password.equals(MD5Util.encrypt(loginDto.getPassword()))){
            BaseContextHandler.setUser(loginDto);
        }else{
            throw new BusinessException("密码不正确");
        }
    }

    @Override
    public void addUser(UserDto userDto) {
        StudentDto studentDto = userDto.getStudentDto();
        studentDto.setLoginName(userDto.getLoginName());
        studentDto.setPassword(userDto.getPassword());
        studentService.add(studentDto);
    }

    @Override
    public void checkUserExist(String loginName) {
        StudentInfo studentInfo = studentService.queryByLoginName(loginName);
        if(studentInfo != null) {
            throw new BusinessException("登录名已存在，请换一个名字");
        }
    }
}