package com.sd.service;

import com.github.pagehelper.PageInfo;
import com.sd.common.util.CollectionUtil;
import com.sd.dto.Page;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import reactor.fn.Consumer;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @Package: com.sd.service.BaseService
 * @Description: 
 * @author sudan
 * @date 2020/8/19 10:25
 */
 
public class BaseService {

    protected <T> Example  createExample(Class<T> clazz, Consumer<Example.Criteria>... consumer){
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        if(ArrayUtils.isNotEmpty(consumer)){
            Arrays.stream(consumer).forEach(bean -> bean.accept(criteria));
        }
        return example;
    }

    protected <T> Example createExampleByCondition(Class<T> clazz,Object condition){
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
      try {
          Field[] declaredFields = condition.getClass().getDeclaredFields();
          for (Field field : declaredFields){
              field.setAccessible(true);
              Object value = field.get(condition);
              if(value != null && !"".equals(value)){
                 criteria.andEqualTo(field.getName(),value);
              }
          }
      } catch (IllegalAccessException e) {
          e.printStackTrace();
      }
      return example;
    }

    protected  <T> Page listToPage(List<T> list) {
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        PageInfo pageInfo = PageInfo.of(list);
        return new Page(pageInfo.getPageNum() - 1, pageInfo.getTotal(), pageInfo.getPageSize(), list);
    }

}