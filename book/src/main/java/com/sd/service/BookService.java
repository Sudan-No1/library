package com.sd.service;

import com.sd.dto.BookDto;
import com.sd.model.BookInfo;

import java.util.List;

/**
 * @Package: com.sd.service.BookService
 * @Description: 
 * @author sudan
 * @date 2020/5/27 18:07
 */
 
 
public interface BookService {

    List<BookDto> list();

    void add(BookDto bookDto);

    BookInfo selectById(String id);


    BookInfo selectByBookNo(String bookNo);

    void update(BookInfo bookInfo);
}