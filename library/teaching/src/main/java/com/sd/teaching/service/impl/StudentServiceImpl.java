package com.sd.teaching.service.impl;

import com.sd.teaching.mapper.StudentInfoMapper;
import com.sd.teaching.model.StudentInfo;
import com.sd.teaching.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

import static com.sd.teaching.common.constant.BaseConstant.SING_STRING_NO;

/**
 * @Package: com.sd.teaching.service.impl.StudentServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/7/14 14:27
 */
 
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public void add(StudentInfo studentInfo) {
        studentInfoMapper.insert(studentInfo);
    }

    @Override
    public void update(StudentInfo studentInfo) {
        Example example = new Example(StudentInfo.class);
        example.createCriteria()
                .andEqualTo("stuNo",studentInfo.getStuNo())
                .andEqualTo("isDeleted",SING_STRING_NO);
        studentInfoMapper.updateByExample(studentInfo,example);
    }

    @Override
    public List<StudentInfo> queryAll() {
        Example example = new Example(StudentInfo.class);
        example.createCriteria()
                .andEqualTo("isDeleted",SING_STRING_NO);
        return studentInfoMapper.selectByExample(example);
    }

    @Override
    public void delete(String stuNo) {
        Example example = new Example(StudentInfo.class);
        example.createCriteria()
                .andEqualTo("stuNo",stuNo)
                .andEqualTo("isDeleted",SING_STRING_NO);
        studentInfoMapper.deleteByExample(example);
    }
}