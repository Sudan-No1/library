package com.sd.service.impl;

import com.sd.dto.BookDto;
import com.sd.mapper.BookMapper;
import com.sd.model.BookInfo;
import com.sd.service.BookService;
import com.sd.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

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
        bookMapper.insert(bookInfo);
    }

    @Override
    public BookDto query(String id) {
        BookInfo bookInfo = bookMapper.selectByPrimaryKey(id);
        return BeanMapper.map(bookInfo,BookDto.class);
    }
}