package com.sd.service.impl;

import com.github.pagehelper.PageHelper;
import com.sd.common.constant.MqConstant;
import com.sd.common.util.BeanMapper;
import com.sd.dto.Page;
import com.sd.dto.es.ESStudent;
import com.sd.dto.student.StudentDto;
import com.sd.dto.student.StudentQueryDto;
import com.sd.mapper.StudentMapper;
import com.sd.model.StudentInfo;
import com.sd.service.BaseService;
import com.sd.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Package: com.sd.service.impl.StudentServiceImpl
 * @Description: 
 * @author sudan
 * @date 2020/5/28 16:34
 */
 
@Service
public class StudentServiceImpl extends BaseService implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void add(StudentDto studentDto) {
        StudentInfo studentInfo = BeanMapper.map(studentDto, StudentInfo.class);
        int i = studentMapper.selectCount(new StudentInfo());
        String studentNo = studentDto.getClassNo()+String.format("%03d", i++);
        studentInfo.setStudentNo(studentNo);
        studentMapper.insert(studentInfo);
        rabbitTemplate.convertAndSend(MqConstant.BOOK_EXCHANGE, MqConstant.BAODAO_ROUTING_KEY,studentNo);
    }

    @Override
    public void update(StudentInfo studentInfo) {
        Example example = super.createExample(StudentInfo.class, criteria -> criteria.andEqualTo("studentNo", studentInfo.getStudentNo()));
        studentMapper.updateByExample(studentInfo,example);
    }

    @Override
    public StudentInfo queryByStudentNo(String studentNo) {
        Example example = super.createExample(StudentInfo.class,criteria -> criteria.andEqualTo("studentNo",studentNo));
        return studentMapper.selectOneByExample(example);
    }

    @Override
    public Page<StudentInfo> queryPage(StudentQueryDto studentQueryDto) {
        PageHelper.startPage(studentQueryDto.getPageNum(), studentQueryDto.getPageSize());
        Example example = super.createExampleByCondition(StudentInfo.class, studentQueryDto);
        List<StudentInfo> studentInfos = studentMapper.selectByExample(example);
        return listToPage(studentInfos);
    }

    @Override
    public StudentInfo queryByLoginName(String loginName) {
        Example example = super.createExample(StudentInfo.class, criteria -> {
            criteria.andEqualTo("loginName", loginName);
        });
        return studentMapper.selectOneByExample(example);
    }

    @Override
    public void esAdd(StudentDto studentDto) {
        ESStudent esStudent = BeanMapper.map(studentDto, ESStudent.class);
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(esStudent).build();
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public List<StudentDto> esQuery(StudentDto studentDto) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(studentDto.getClassNo())){
            boolQueryBuilder.must(QueryBuilders.termQuery("classNo",studentDto.getClassNo()));
        }
        if(StringUtils.isNotBlank(studentDto.getName())){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("name",studentDto.getName()));
        }
        if(StringUtils.isNotBlank(studentDto.getEmail())){
            boolQueryBuilder.must(QueryBuilders.termQuery("email",studentDto.getEmail()));
        }
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        List<ESStudent> esStudents = elasticsearchTemplate.queryForList(query, ESStudent.class);
        List<StudentDto> list = BeanMapper.mapList(esStudents, ESStudent.class, StudentDto.class);
        return list;
    }
}