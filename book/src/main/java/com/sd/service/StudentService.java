package com.sd.service;

import com.sd.model.BorrowInfo;
import com.sd.model.StudentInfo;

/**
 * @Package: com.sd.service.StudentService
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:33
 */
 
 
public interface StudentService {
    StudentInfo selectByNo(String studentNo);
}