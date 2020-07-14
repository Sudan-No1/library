package com.sd.teaching.mapper;

import com.sd.teaching.model.StudentInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface StudentInfoMapper extends Mapper<StudentInfo> {
}