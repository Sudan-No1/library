package com.sd.service.impl;

import com.sd.dto.BookDto;
import com.sd.mapper.BookMapper;
import com.sd.model.BookInfo;
import com.sd.service.BookService;
import com.sd.common.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

/**
 * @Package: com.sd.service.impl.BookServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/27 18:08
 */
 
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookDto> list() {
        List<BookInfo> bookInfoList = bookMapper.selectAll();
        List<BookDto> bookDtoList = BeanMapper.mapList(bookInfoList, BookInfo.class, BookDto.class);
        return bookDtoList;
    }

    @Override
    public void add(BookDto bookDto) {
        BookInfo bookInfo = BeanMapper.map(bookDto, BookInfo.class);
        bookInfo.setActive(1);
        bookMapper.insert(bookInfo);
    }

    @Override
    public BookInfo selectById(String id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public BookInfo selectByBookNo(String bookNo) {
        Example example = new Example(BookInfo.class);
        example.createCriteria()
                .andEqualTo("bookNo",bookNo);
        return bookMapper.selectOneByExample(example);
    }

    @Override
    public void update(BookInfo bookInfo) {
        bookMapper.updateByPrimaryKey(bookInfo);
    }
}