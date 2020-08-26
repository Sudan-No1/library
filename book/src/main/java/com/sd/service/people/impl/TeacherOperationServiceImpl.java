package com.sd.service.people.impl;

import com.sd.common.constant.PeopleTypeEnum;
import com.sd.common.exception.BusinessException;
import com.sd.common.util.MD5Util;
import com.sd.dto.BaseContextHandler;
import com.sd.dto.LoginDto;
import com.sd.dto.UserDto;
import com.sd.dto.borrow.BookBorrowDto;
import com.sd.dto.teacher.TeacherDto;
import com.sd.model.TeacherInfo;
import com.sd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sd.common.constant.BusinessConstant.LOGIN_USER;

/**
 * @Package: TeacherOperationServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/21 9:42
 */
 
@Service
public class TeacherOperationServiceImpl extends OperationAbstractServiceImpl {

    @Autowired
    private TeacherService teacherService;

    @Value("${borrow.student.bookNum}")
    private Integer bookNum;

    @Value("${borrow.student.borrowDays}")
    private Integer borrowDays;

    @Value("${borrow.student.fine}")
    private BigDecimal fine;

    @Override
    public PeopleTypeEnum getCode() {
        return PeopleTypeEnum.PEOPLE_TYPE_ENUM_TEACHER;
    }

    @Override
    public BookBorrowDto getBorrowInfo() {
        return new BookBorrowDto(bookNum,borrowDays,fine);
    }

    @Override
    public void login(LoginDto loginDto) {
        TeacherInfo teacherInfo = teacherService.queryByLoginName(loginDto.getLoginName());
        if(teacherInfo == null){
            throw new BusinessException("用户不存在");
        }
        String password = teacherInfo.getPassword();
        if(password.equals(MD5Util.encrypt(loginDto.getPassword()))){
            BaseContextHandler.setUser(loginDto);
        }else{
            throw new BusinessException("密码不正确");
        }
    }

    @Override
    public void addUser(UserDto userDto) {
        TeacherDto teacherDto = userDto.getTeacherDto();
        teacherDto.setLoginName(userDto.getLoginName());
        teacherDto.setPassword(userDto.getPassword());
        teacherService.add(teacherDto);
    }

    @Override
    public void checkUserExist(String loginName) {
        TeacherInfo teacherInfo = teacherService.queryByLoginName(loginName);
        if(teacherInfo != null){
            throw new BusinessException("登录名已存在，请换一个名字");
        }
    }
}