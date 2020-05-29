package com.sd.mapper;


import com.sd.model.ExpiredBorrowInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Package: com.sd.mapper.BookMapper
 * @Description: 借阅过期表
 * @author sudan
 * @date 2020/5/27 18:08
 */
 
@Repository
public interface ExpiredBorrowMapper extends Mapper<ExpiredBorrowInfo> {
}