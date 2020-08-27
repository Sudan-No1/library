package com.sd.service;

import com.sd.dto.book.BookAddDto;
import com.sd.dto.book.BookDto;
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

    void add(BookAddDto bookDto);

    BookInfo selectById(String id);


    BookInfo selectByBookNo(String bookNo);

    void update(BookInfo bookInfo);

    /**
     * @Description 更新库存信息
     * @param bookInfo
     * @return int
     * @throws
     * @author sudan
     * @date 2020/8/21 15:25
     */
    int updateInventory(BookInfo bookInfo);
}