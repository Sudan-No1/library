package com.sd.mapper;


import com.sd.model.StudentInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Package: com.sd.mapper.BookMapper
 * @Description: 学生表
 * @author sudan
 * @date 2020/5/27 18:08
 */
 
@Repository
public interface StudentMapper extends Mapper<StudentInfo> {
}