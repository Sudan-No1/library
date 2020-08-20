package com.sd.mapper;

import com.sd.model.TeacherInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TeacherInfoMapper extends Mapper<TeacherInfo> {
}