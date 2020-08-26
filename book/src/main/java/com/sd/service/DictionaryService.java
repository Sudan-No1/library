package com.sd.service;

import com.sd.dto.mongo.Dictionary;

import java.util.List;

/**
 * @Package: com.sd.service.DictionaryService
 * @Description: 
 * @author sudan
 * @date 2020/8/26 16:33
 */
 
 
public interface DictionaryService {
    void add(Dictionary dictionary);

    void update(Dictionary dictionary);

    void delete(String dictTypeCode);

    void delete(String dictTypeCode, String dictCode);

    List<Dictionary> queryList(String dictTypeCode);

    String queryName(String dictTypeCode, String dictCode);
}