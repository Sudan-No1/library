package com.sd.teaching.service.impl;

import com.sd.teaching.common.constant.BaseConstant;
import com.sd.teaching.mapper.BookInfoMapper;
import com.sd.teaching.model.BookInfo;
import com.sd.teaching.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

import static com.sd.teaching.common.constant.BaseConstant.SING_STRING_NO;

/**
 * @Package: com.sd.teaching.service.impl.BookServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/7/14 18:01
 */
 
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public void add(BookInfo bookInfo) {
        bookInfoMapper.insert(bookInfo);
    }

    @Override
    public void update(BookInfo bookInfo) {
        Example example = new Example(BookInfo.class);
        example.createCriteria()
                .andEqualTo("bookNo",bookInfo.getBookNo())
                .andEqualTo("isDeleted", SING_STRING_NO);
        bookInfoMapper.updateByExampleSelective(bookInfo,example);
    }

    @Override
    public List<BookInfo> queryAll() {
        Example example = new Example(BookInfo.class);
        example.createCriteria()
                .andEqualTo("isDeleted", SING_STRING_NO);
        example.orderBy("createTime").desc();
        return bookInfoMapper.selectByExample(example);
    }

    @Override
    public void delete(String bookNo) {
        Example example = new Example(BookInfo.class);
        example.createCriteria()
                .andEqualTo("bookNo", bookNo);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookNo(bookNo);
        bookInfo.setIsDeleted(SING_STRING_NO);
        bookInfoMapper.updateByExampleSelective(bookInfo,example);
    }
}