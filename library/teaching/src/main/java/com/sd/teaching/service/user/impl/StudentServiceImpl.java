package com.sd.teaching.service.user.impl;

import com.sd.teaching.common.enums.UserEnum;

/**
 * @Package: com.sd.teaching.service.user.impl.StudentServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/7/14 11:21
 */
 
 
public class StudentServiceImpl extends AbstractUserServiceImpl{

    @Override
    public UserEnum getEnum() {
        return UserEnum.USER_SERVICE_STUDENT;
    }

}