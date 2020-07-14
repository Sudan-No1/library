package com.sd.teaching.service;

import com.sd.teaching.model.BookInfo;

import java.util.List;

/**
 * @Package: com.sd.teaching.service.BookService
 * @Description: 
 * @author sudan
 * @date 2020/7/14 17:43
 */
 
 
public interface BookService {
    void add(BookInfo bookInfo);

    void update(BookInfo bookInfo);

    List<BookInfo> queryAll();

    void delete(String bookNo);
}