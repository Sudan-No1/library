package com.sd.service.impl;

import com.sd.dto.mongo.Dictionary;
import com.sd.service.DictionaryService;
import com.sd.service.MongodbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.sd.service.impl.DictionaryServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/8/26 16:34
 */
 
@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private MongodbService mongodbService;

    @Override
    public void add(Dictionary dictionary) {
        mongodbService.save(dictionary);
    }

    @Override
    public void update(Dictionary dictionary) {
        mongodbService.update(dictionary);
    }

    @Override
    public void delete(String dictTypeCode) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictTypeCode(dictTypeCode);
        mongodbService.delete(dictionary);
    }

    @Override
    public void delete(String dictTypeCode, String dictCode) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictTypeCode(dictTypeCode);
        dictionary.setDictCode(dictCode);
        mongodbService.delete(dictionary);
    }

    @Override
    public List<Dictionary> queryList(String dictTypeCode) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictTypeCode(dictTypeCode);
        return mongodbService.find(dictionary,"dictOrder");
    }

    @Override
    public String queryName(String dictTypeCode, String dictCode) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictTypeCode(dictTypeCode);
        dictionary.setDictCode(dictCode);
        Dictionary dbDictionary = mongodbService.findOne(dictionary);
        if(dbDictionary != null){
            return dbDictionary.getDictName();
        }
        return null;
    }
}