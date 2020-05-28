package com.sd.service.impl;

import com.sd.mapper.BookMapper;
import com.sd.model.BookInfo;
import com.sd.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BookInfo> list() {
        return bookMapper.selectAll();
    }

    @Override
    public void add(BookInfo bookInfo) {
        bookMapper.insert(bookInfo);
    }

    @Override
    public BookInfo query(String id) {
        return bookMapper.selectByPrimaryKey(id);
    }
}