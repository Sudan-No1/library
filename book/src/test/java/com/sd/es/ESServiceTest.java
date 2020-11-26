package com.sd.es;

import com.sd.Application;
import com.sd.common.util.BeanMapper;
import com.sd.dto.es.ESStudent;
import com.sd.dto.student.StudentDto;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Package: com.sd.es.ESServiceTest
 * @Description: 
 * @author sudan
 * @date 2020/11/20 14:38
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ESServiceTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testEsSearch(){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("name","张三"));
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        List<ESStudent> esStudents = elasticsearchTemplate.queryForList(query, ESStudent.class);
        List<StudentDto> list = BeanMapper.mapList(esStudents, ESStudent.class, StudentDto.class);
        System.out.println(list);
    }



}