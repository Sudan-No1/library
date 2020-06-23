package com.sd.service;

import com.sd.controller.BookController;
import com.sd.model.BookNoConfig;

/**
 * @Package: com.sd.service.BookNoConfigService
 * @Description: 
 * @author sudan
 * @date 2020/6/23 14:42
 */
 
 
public interface BookNoConfigService {
    String getBookNo(String name);

    BookNoConfig selectByPrefix(String prefix);

    int save(BookNoConfig bookNoConfig);

    int update(BookNoConfig bookNoConfig);
}