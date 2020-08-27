package com.sd.service.impl;

import com.sd.dto.book.BookAddDto;
import com.sd.dto.book.BookDto;
import com.sd.mapper.BookMapper;
import com.sd.model.BookInfo;
import com.sd.service.BaseService;
import com.sd.service.BookNoConfigService;
import com.sd.service.BookService;
import com.sd.common.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Package: com.sd.service.impl.BookServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/27 18:08
 */
 
@Service
public class BookServiceImpl extends BaseService implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookNoConfigService bookNoConfigService;


    @Override
    public List<BookDto> list() {
        List<BookInfo> bookInfoList = bookMapper.selectAll();
        List<BookDto> bookDtoList = BeanMapper.mapList(bookInfoList, BookInfo.class, BookDto.class);
        return bookDtoList;
    }

    @Override
    public void add(BookAddDto bookDto) {
        String bookNo = bookNoConfigService.getBookNo(bookDto.getName());
        BookInfo bookInfo = BeanMapper.map(bookDto, BookInfo.class);
        bookInfo.setBookNo(bookNo);
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

    @Override
    public int updateInventory(BookInfo bookInfo) {
        Integer inventory = bookInfo.getInventory();
        if(inventory.intValue() == 1){
            bookInfo.setActive(0);
        }
        bookInfo.setInventory(inventory-1);
        Example example = super.createExample(BookInfo.class, criteria -> {
            criteria.andEqualTo("inventory", inventory);
            criteria.andEqualTo("bookNo", bookInfo.getBookNo());
        });
        return bookMapper.updateByExample(bookInfo,example);
    }
}