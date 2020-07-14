package com.sd.teaching.mapper;

import com.sd.teaching.model.BookInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BookInfoMapper extends Mapper<BookInfo> {
}