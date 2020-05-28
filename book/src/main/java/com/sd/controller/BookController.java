package com.sd.controller;

import com.sd.dto.BookDto;
import com.sd.model.BookInfo;
import com.sd.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Package: com.sd.controller.BookController
 * @Description: 
 * @author sudan
 * @date 2020/5/27 17:58
 */
 
@RestController
@Api(value = "图书管理")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("list")
    public List<BookDto> list(){
        return bookService.list();
    }

    @PostMapping("add")
    public void add(BookDto bookDto){
        bookService.add(bookDto);
    }

    @PostMapping("query/{id}")
    public BookDto query(@PathVariable("id") String id){
        return bookService.query(id);
    }

}