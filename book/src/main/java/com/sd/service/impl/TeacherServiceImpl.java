package com.sd.service.impl;

import com.github.pagehelper.PageHelper;
import com.sd.common.util.BeanMapper;
import com.sd.dto.Page;
import com.sd.dto.PageDto;
import com.sd.dto.teacher.TeacherDto;
import com.sd.dto.teacher.TeacherQueryDto;
import com.sd.mapper.TeacherInfoMapper;
import com.sd.model.StudentInfo;
import com.sd.model.TeacherInfo;
import com.sd.service.BaseService;
import com.sd.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Package: com.sd.service.impl.TeacherServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/20 16:12
 */
 
@Service
public class TeacherServiceImpl extends BaseService implements TeacherService {

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Override
    public void add(TeacherDto teacherDto) {
        TeacherInfo teacherInfo = BeanMapper.map(teacherDto, TeacherInfo.class);
        int i = teacherInfoMapper.selectCount(new TeacherInfo());
        String teacherNo = teacherDto.getClassNo()+String.format("%02d", i++);
        teacherInfo.setTeacherNo(teacherNo);
        teacherInfoMapper.insert(teacherInfo);
    }

    @Override
    public void update(TeacherDto teacherDto) {
        TeacherInfo teacherInfo = BeanMapper.map(teacherDto, TeacherInfo.class);
        Example example = super.createExample(TeacherInfo.class, criteria -> {
            criteria.andEqualTo("teacherNo", teacherDto.getTeacherNo());
        });
        teacherInfoMapper.updateByExample(teacherInfo,example);
    }

    @Override
    public TeacherDto queryByStudentNo(String teacherNo) {
        Example example = super.createExample(TeacherInfo.class, criteria -> {
            criteria.andEqualTo("teacherNo", teacherNo);
        });
        TeacherInfo teacherInfo = teacherInfoMapper.selectOneByExample(example);
        return  BeanMapper.map(teacherInfo,TeacherDto.class);
    }

    @Override
    public Page<TeacherDto> queryPage(TeacherQueryDto teacherQueryDto) {
        PageHelper.startPage(teacherQueryDto.getPageNum(), teacherQueryDto.getPageSize());
        Example example = super.createExampleByCondition(TeacherInfo.class, teacherQueryDto);
        List<TeacherInfo> teacherInfos = teacherInfoMapper.selectByExample(example);
        return listToPage(teacherInfos);
    }
}