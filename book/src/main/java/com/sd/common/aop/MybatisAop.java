package com.sd.common.aop;

import com.sd.common.util.BeanMapper;
import com.sd.common.util.CollectionUtil;
import com.sd.common.util.IdWorker;
import com.sd.dto.es.ESStudent;
import com.sd.model.StudentInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @Package: com.sd.common.aop.MybatisAop
 * @Description: 
 * @author sudan
 * @date 2020/5/27 20:42
 */

@Component
@Aspect
@Slf4j
@Order(-1)
public class MybatisAop {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Around("execution(* tk.mybatis.mapper.common.base.insert..*.*(..)) || " +
            "execution(* tk.mybatis.mapper.common.special..*.*(..))")
    public Object doAroundInsert(ProceedingJoinPoint pjp)throws Throwable{
        Object[] args = pjp.getArgs();
        if(args != null && args.length>0){
            for (Object entity : args){
                if(entity instanceof Collection){
                    for (Object obj : (Collection) entity){
                        initInsertEntity(obj);
                    }
                }else {
                    initInsertEntity(entity);
                }
            }
        }
        Object result = null;
        try {
            result = pjp.proceed();
        }catch (Exception e){
            log.info("插入数据库处理异常");
            if (e instanceof UncategorizedSQLException) {
                log.info("捕获插入数据库处理异常");
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }

    /**
     * 拦截TK插件中调用的Update方法
     */
    @Around("execution(* tk.mybatis.mapper.common.base.update..*.*(..)) || " +
            "execution(* tk.mybatis.mapper.common.example..Update*.*(..))")
    public Object doAroundUpdate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取目标方法的参数信息
        Object[] arguments = proceedingJoinPoint.getArgs();
        if (arguments != null && arguments.length > 0) {
            Object entity = arguments[0];
            initUpdateEntity(entity);
        }
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            log.info("更新数据库处理异常");
            if (e instanceof UncategorizedSQLException) {
                log.info("捕获更新数据库处理异常");
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }

    private void initInsertEntity(Object object) {
        syncDataToEs(object);
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
        beanWrapper.setPropertyValue("id",idWorker.nextId());
        beanWrapper.setPropertyValue("creatorId", "system");
        beanWrapper.setPropertyValue("updatorId", "system");
        beanWrapper.setPropertyValue("createTime", new Date());
        beanWrapper.setPropertyValue("updateTime", new Date());
    }

    private void syncDataToEs(Object object) {
       /* BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name","李四"));
      *//*  boolQueryBuilder.must(QueryBuilders.matchQuery("classNo","101847"));
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("email","12@qq.com"));*//*
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        List<ESStudent> esStudents = elasticsearchTemplate.queryForList(query, ESStudent.class);
        System.out.println(esStudents);*/
        if(object instanceof StudentInfo){
            StudentInfo studentInfo = (StudentInfo)object;
            ESStudent esStudent = BeanMapper.map(studentInfo, ESStudent.class);
            esStudent.setId(studentInfo.getStudentNo());
            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchPhraseQuery("name", esStudent.getName()))
                    .build();
            List<ESStudent> esStudents = elasticsearchTemplate.queryForList(query, ESStudent.class);
            if(CollectionUtil.isNotEmpty(esStudents)){
                for (ESStudent esStudent1 : esStudents){
                    elasticsearchTemplate.delete(ESStudent.class,esStudent1.getId());
                }
            }

            IndexQuery indexQuery = new IndexQueryBuilder().withObject(esStudent).build();
            elasticsearchTemplate.index(indexQuery);
        }
    }

    private void initUpdateEntity(Object object) {
        syncDataToEs(object);
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
        beanWrapper.setPropertyValue("updatorId", "system");
        beanWrapper.setPropertyValue("updateTime", new Date());
    }
}