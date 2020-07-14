package com.sd.teaching.service.mongo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

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
    private MongoTemplate mongoTemplate;
    @Autowired
    public MongodbService( MongoTemplate mongoTemplate,MongoDbFactory mongoDbFactory){
        this.mongoTemplate=mongoTemplate;
    }
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
      * @Description:
      * @param objectToSave :
      * @param collectionName :
      * @return T
      * @throws
      * @author Yao.Jie
      * @date 2019/9/24 17:07
       */
    public <T> void save(T objectToSave, String collectionName) {
        mongoTemplate.save(objectToSave,collectionName);
    }

     /**
       * @Description: 文件查询
       * @param zlass :
       * @return java.util.List<T>
       * @throws
       * @author Yao.Jie
       * @date 2019/9/25 15:19
        */
       <T> List<T> findAll(Class zlass){
          return mongoTemplate.findAll(zlass);
        }


    /**
      * @Description: 根据数据对象中的id删除数据，集合为数据对象中@Document 注解所配置的collection
      * @param obj :   数据对象
      * @return void
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:54
       */
    public  void remove(Object obj) {
        mongoTemplate.remove(obj);
    }


    /**
      * @Description: 指定集合 根据数据对象中的id删除数据
      * @param obj : 数据对象
      * @param collectionName :    集合名
      * @return void
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:55
       */

    public  void remove(Object obj, String collectionName) {

        mongoTemplate.remove(obj, collectionName);
    }

    /**
      * @Description: 根据key，value到指定集合删除数据
      * @param key :  键
      * @param value : 值
      * @param collectionName :  集合名称
      * @return void
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:55
       */

    public  void removeById(String key, Object value, String collectionName) {
        Criteria criteria = Criteria.where(key).is(value);
        criteria.and(key).is(value);
        Query query = Query.query(criteria);
        mongoTemplate.remove(query, collectionName);
    }

    /**
      * @Description: 指定集合 修改数据，且仅修改找到的第一条数据
      * @param accordingKey : 修改条件 key
      * @param accordingValue : 修改条件 value
      * @param updateKeys :  修改内容 key数组
      * @param updateValues : 修改内容 value数组
      * @param collectionName :   集合名
      * @return void
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:55
       */
    public  void updateFirst(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
        String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
       mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     *  @Description: 指定集合 修改数据，且修改所找到的所有数据
     * @param accordingKey 修改条件 key
     * @param accordingValue  修改条件 value
     * @param updateKeys 修改内容 key数组
     * @param updateValues: 修改内容 value数组
     * @param collectionName :集合名
     * @return void
     * @throws
     * @author Yao.Jie
     * @date 2019/9/25 17:55
     * */
    public  void updateMulti(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
        String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
       mongoTemplate.updateMulti(query, update, collectionName);
    }


    /**
      * @Description: 根据条件查询出所有结果集 集合为数据对象中@Document 注解所配置的collection
      * @param obj :  数据对象
      * @param findKeys :  查询条件 key
      * @param findValues :   查询条件 value
      * @return java.util.List<? extends java.lang.Object>
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:57
       */
    public  List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        List<? extends Object> resultList = mongoTemplate.find(query, obj.getClass());
        return resultList;
    }


    /**
      * @Description: 指定集合 根据条件查询出所有结果集
      * @param obj :   数据对象
      * @param findKeys : 查询条件 key
      * @param findValues : 查询条件 value
      * @param collectionName :  集合名
      * @return java.util.List<? extends java.lang.Object>
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:58
       */
    public  List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues, String collectionName) {
        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        List<? extends Object> resultList = mongoTemplate.find(query, obj.getClass(), collectionName);
        return resultList;
    }


    /**
      * @Description:  指定集合 根据条件查询出所有结果集 并排倒序
      * @param obj : 数据对象
      * @param findKeys :   查询条件 key
      * @param findValues : 查询条件 value
      * @param collectionName : 集合名
      * @param sort :   排序字段
      * @return java.util.List<? extends java.lang.Object>
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 18:00
       */

    public  List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues, String collectionName ,String sort) {
        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        query.with(new Sort(Sort.Direction.DESC, sort));
        List<? extends Object> resultList = mongoTemplate.find(query, obj.getClass(), collectionName);
        return resultList;
    }

    /**
      * @Description: 根据条件查询出符合的第一条数据 集合为数据对象中 @Document 注解所配置的collection
      * @param obj : 数据对象
      * @param findKeys :   查询条件 key
      * @param findValues :   查询条件 value
      * @return java.lang.Object
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 18:00
       */
    public  Object findOne(Object obj, String[] findKeys, Object[] findValues) {
        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Object resultObj = mongoTemplate.findOne(query, obj.getClass());
        return resultObj;
    }
    /**
      * @Description:
      * @param obj : 数据对象
      * @param findKeys : 查询条件 key
      * @param findValues :  查询条件 value
      * @param collectionName :
      * @return java.lang.Object  集合名
      * @throws
      * @author Yao.Jie
      * @date 2019/9/25 17:59
       */
    public  Object findOne(Object obj, String[] findKeys, Object[] findValues, String collectionName) {
        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Object resultObj = mongoTemplate.findOne(query, obj.getClass(), collectionName);
        return resultObj;
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

}
