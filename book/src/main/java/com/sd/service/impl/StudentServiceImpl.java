package com.sd.service.impl;

import com.github.pagehelper.PageHelper;
import com.sd.common.util.BeanMapper;
import com.sd.dto.Page;
import com.sd.dto.student.StudentDto;
import com.sd.dto.student.StudentQueryDto;
import com.sd.mapper.StudentMapper;
import com.sd.model.BorrowInfo;
import com.sd.model.StudentInfo;
import com.sd.service.BaseService;
import com.sd.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Package: com.sd.service.impl.StudentServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:34
 */
 
@Service
public class StudentServiceImpl extends BaseService implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void add(StudentDto studentDto) {
        StudentInfo studentInfo = BeanMapper.map(studentDto, StudentInfo.class);
        int i = studentMapper.selectCount(new StudentInfo());
        String studentNo = studentDto.getClassNo()+String.format("%03d", i++);
        studentInfo.setStudentNo(studentNo);
        studentMapper.insert(studentInfo);
    }

    @Override
    public void update(StudentInfo studentInfo) {
        Example example = super.createExample(StudentInfo.class, criteria -> criteria.andEqualTo("studentNo", studentInfo.getStudentNo()));
        studentMapper.updateByExample(studentInfo,example);
    }

    @Override
    public StudentInfo queryByStudentNo(String studentNo) {
        Example example = super.createExample(StudentInfo.class,criteria -> criteria.andEqualTo("studentNo",studentNo));
        return studentMapper.selectOneByExample(example);
    }

    @Override
    public Page<StudentInfo> queryPage(StudentQueryDto studentQueryDto) {
        PageHelper.startPage(studentQueryDto.getPageNum(), studentQueryDto.getPageSize());
        Example example = super.createExampleByCondition(StudentInfo.class, studentQueryDto);
        List<StudentInfo> studentInfos = studentMapper.selectByExample(example);
        return listToPage(studentInfos);
    }
}