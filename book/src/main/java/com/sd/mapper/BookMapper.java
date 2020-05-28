package com.sd.mapper;


import com.sd.model.BookInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Package: com.sd.mapper.BookMapper
 * @Description: 
 * @author sudan
 * @date 2020/5/27 18:08
 */
 
@Repository
public interface BookMapper extends Mapper<BookInfo> {
}