package com.sd.teaching.controller;

import com.sd.teaching.common.annotions.BehaviorLog;
import com.sd.teaching.dto.InvokeResult;
import com.sd.teaching.model.BookInfo;
import com.sd.teaching.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package: com.sd.teaching.controller.BookController
 * @Description: 图书信息维护
 * @author sudan
 * @date 2020/7/14 17:38
 */
 
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @BehaviorLog("新增图书")
    @PostMapping("/add")
    public InvokeResult<Void> add(@RequestBody BookInfo bookInfo){
        bookService.add(bookInfo);
        return InvokeResult.success();
    }

    @BehaviorLog("修改图书")
    @PostMapping("/update")
    public InvokeResult<Void> update(@RequestBody BookInfo bookInfo){
        bookService.update(bookInfo);
        return InvokeResult.success();
    }


    @BehaviorLog("查询图书")
    @PostMapping("/queryAll")
    public InvokeResult<List<BookInfo>> queryAll(){
        List<BookInfo> list = bookService.queryAll();
        return InvokeResult.success(list);
    }

    @BehaviorLog("删除图书")
    @GetMapping("/delete/{bookNo}")
    public InvokeResult<Void> delete(@PathVariable String bookNo){
        bookService.delete(bookNo);
        return InvokeResult.success();
    }


}