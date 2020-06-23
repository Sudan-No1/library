package com.sd.controller;

import com.sd.common.annotation.BehaviorLog;
import com.sd.common.annotation.RepeatSubmit;
import com.sd.common.util.BeanMapper;
import com.sd.dto.BookDto;
import com.sd.model.BookInfo;
import com.sd.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.sd.common.constant.RedisConstant.REPEATSUBMIT_BOOK;

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
    @BehaviorLog("查询所有图书")
    public List<BookDto> list(){
        return bookService.list();
    }

    @PostMapping("add")
    @BehaviorLog("新增图书")
    @RepeatSubmit(prefix = REPEATSUBMIT_BOOK,time = 5)
    public void add(@Valid BookDto bookDto){
        bookService.add(bookDto);
    }

    @PostMapping("query/{bookNo}")
    @BehaviorLog("根据订单编号查询图书")
    public BookDto selectByBookNo(@PathVariable String bookNo){
        BookInfo bookInfo = bookService.selectByBookNo(bookNo);
        BookDto bookDto = BeanMapper.map(bookInfo, BookDto.class);
        return bookDto;
    }

}