package com.sd.service;

import com.sd.model.BookInfo;

import java.util.List;

/**
 * @Package: com.sd.service.BookService
 * @Description: 
 * @author sudan
 * @date 2020/5/27 18:07
 */
 
 
public interface BookService {

    List<BookInfo> list();

    void add(BookInfo bookInfo);

    BookInfo query(String id);
}