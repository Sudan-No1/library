package com.sd.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description:  {@link MongoTemplate} mogo工具类
 * @Package: com.yixin.mogo.MogodbService
 * @Author Yao.Jie
 * @date 2019/9/24 15:38
 */
@Component
@Slf4j
public class MongodbService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
      * @Description: 保存Document
      * @param objectToSave : docuent对象
      * @return void
      * @throws
      * @author Yao.Jie
      * @date 2019/9/24 15:42
       */
    public <T> void save(T objectToSave) {
        mongoTemplate.save(objectToSave);
    }

    /**
      * @Description:    查询出所有结果集 集合为数据对象中 @Document 注解所配置的collection
      * @param obj :   数据对象
      * @return java.util.List<? extends java.lang.Object>
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:58
       */
    public  List<? extends Object> findAll(Object obj) {
        List<? extends Object> resultList = mongoTemplate.findAll(obj.getClass());
        return resultList;
    }

    /**
      * @Description: 指定集合 查询出所有结果集
      * @param obj :  数据对象
      * @param collectionName :  集合名
      * @return java.util.List<? extends java.lang.Object>
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 15:44
       */
    public   List<? extends Object> findAll(Object obj, String collectionName) {
        List<? extends Object> resultList = mongoTemplate.findAll(obj.getClass(), collectionName);
        return resultList;
    }

    /**
     * @Description 更新数据
     * @param obj
     * @return void
     * @throws
     * @author sudan
     * @date 2020/8/26 17:00
     */
    public void update(Object obj) {
        try {
            Class<?> clazz = obj.getClass();
            Field id = clazz.getDeclaredField("id");
            id.setAccessible(true);
            Object idValue = id.get(obj);
            Field[] declaredFields = clazz.getDeclaredFields();
            Criteria criteria = Criteria.where("_id").is(idValue);
            Query query = Query.query(criteria);
            Update update = new Update();
            for (int i = 0; i < declaredFields.length; i++) {
                Field declaredField = declaredFields[i];
                declaredField.setAccessible(true);
                Object value = declaredField.get(obj);
                if(value != null){
                    update.set(declaredField.getName(),value);
                }
            }
            Document annotation = clazz.getAnnotation(Document.class);
            String collection = annotation.collection();
            mongoTemplate.updateFirst(query, update, collection);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Object obj) {
        Query query = createQuery(obj);
        Document annotation =  obj.getClass().getAnnotation(Document.class);
        String collection = annotation.collection();
        mongoTemplate.remove(query, collection);
    }

    public <T> List<T> find(T obj,String sort) {
        Query query = createQuery(obj);
        query.with(new Sort(Sort.Direction.ASC, sort));
        return (List<T>)mongoTemplate.find(query, obj.getClass());
    }

    private Query createQuery(Object obj){
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            Criteria criteria = new Criteria();
            for (Field field : declaredFields){
                field.setAccessible(true);
                Object value = field.get(obj);
                if(value != null){
                    criteria.and(field.getName()).is(value);
                }
            }
            return Query.query(criteria);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public <T> T findOne(T obj) {
        Query query = createQuery(obj);
        return (T)mongoTemplate.findOne(query, obj.getClass());
    }
}
