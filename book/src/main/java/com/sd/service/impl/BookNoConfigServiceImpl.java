package com.sd.service.impl;

import com.sd.common.util.PinyinUtil;
import com.sd.mapper.BookNoConfigMapper;
import com.sd.model.BookNoConfig;
import com.sd.service.BookNoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import static com.sd.common.constant.BusinessConstant.BOOK_NO_INIT_SERIAL;

/**
 * @Package: com.sd.service.impl.BookNoConfigServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/6/23 14:43
 */
 
@Service
public class BookNoConfigServiceImpl implements BookNoConfigService {

    @Autowired
    private BookNoConfigMapper bookNoConfigMapper;

    @Override
    public String getBookNo(String name) {
        String headerChar = PinyinUtil.getPinyinHeaderChar(name);
        BookNoConfig bookNoConfig = selectByPrefix(headerChar);
        String bookNo;
        if(bookNoConfig == null){
            bookNoConfig = new BookNoConfig();
            bookNoConfig.setPrefix(headerChar);
            bookNoConfig.setSerialNumber(BOOK_NO_INIT_SERIAL);
            bookNoConfig.setVersion(0);
            bookNo = headerChar + BOOK_NO_INIT_SERIAL;
            save(bookNoConfig);
        }else{
            String serialNumber = bookNoConfig.getSerialNumber();
            Integer num = Integer.valueOf(serialNumber);
            String serialNoPrefix = serialNumber.substring(0,serialNumber.indexOf(String.valueOf(num)));
            String newSerialNumber = String.valueOf( num+ 1);
            bookNo = bookNoConfig.getPrefix() + serialNoPrefix + newSerialNumber;
            bookNoConfig.setSerialNumber(serialNoPrefix + newSerialNumber);
            update(bookNoConfig);
        }
        return bookNo;
    }

    @Override
    public BookNoConfig selectByPrefix(String prefix) {
        Example example = new Example(BookNoConfig.class);
        example.createCriteria()
                .andEqualTo("prefix",prefix);
        return bookNoConfigMapper.selectOneByExample(example);
    }

    @Override
    public int save(BookNoConfig bookNoConfig) {
        return bookNoConfigMapper.insert(bookNoConfig);
    }

    @Override
    public int update(BookNoConfig bookNoConfig) {
        return bookNoConfigMapper.updateByPrimaryKey(bookNoConfig);
    }
}