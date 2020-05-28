package com.sd.service.impl;

import com.sd.mapper.StudentMapper;
import com.sd.model.BorrowInfo;
import com.sd.model.StudentInfo;
import com.sd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Package: com.sd.service.impl.StudentServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:34
 */
 
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentInfo selectByNo(String studentNo) {
        Example example = new Example(StudentInfo.class);
        example.createCriteria().andEqualTo("studentNo",studentNo);
        return studentMapper.selectOneByExample(example);
    }
}