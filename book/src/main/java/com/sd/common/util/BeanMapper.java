package com.sd.common.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory.Builder;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.sd.common.util.BeanMapper
 * @Description: 
 * @author sudan
 * @date 2020/5/28 10:54
 */
 
 
public class BeanMapper {

    private static MapperFactory mapperFactory = new Builder().mapNulls(false).build();
    private static MapperFacade mapper;
    private BeanMapper(){}

    public static <S,D>  void map(S sourceObject, D destinationObject){
        if(sourceObject != null && destinationObject != null){
            mapper.map(sourceObject,destinationObject);
        }
    }

    public static <S,D> D  map(S sourceObject, Class<D> destinationClass){
           return mapper.map(sourceObject, destinationClass);
    }

    public static <S,D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass){
        return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass),TypeFactory.valueOf(destinationClass));
    }

    static {
        mapperFactory.getConverterFactory().registerConverter(new BeanMapper.DateTimeConverter());
        mapperFactory.getConverterFactory().registerConverter(new BeanMapper.BigDecimalConverter());
        mapperFactory.getConverterFactory().registerConverter(new BeanMapper.StringToIntegerConverter());
        mapper = mapperFactory.getMapperFacade();
    }

    private static class DateTimeConverter extends BidirectionalConverter<String, Date> {
        private DateTimeConverter() {
        }

        public Date convertTo(String source, Type<Date> destinationType, MappingContext mappingContext) {
            if (StringUtils.isBlank(source)) {
                return null;
            } else {
                try {
                    return source.length() >= 11 ? DateFormatUtil.pareDate("yyyy-MM-dd HH:mm:ss", source) : DateFormatUtil.pareDate("yyyy-MM-dd", source);
                } catch (ParseException var5) {
                    return null;
                }
            }
        }

        public String convertFrom(Date source, Type<String> destinationType, MappingContext mappingContext) {
            return DateFormatUtil.formatDate(source);
        }
    }

    private static class BigDecimalConverter extends BidirectionalConverter<BigDecimal, String> {
        private BigDecimalConverter() {
        }

        public String convertTo(BigDecimal source, Type<String> destinationType, MappingContext mappingContext) {
            return source.toString();
        }

        public BigDecimal convertFrom(String source, Type<BigDecimal> destinationType, MappingContext mappingContext) {
            return StringUtils.isNotBlank(source) ? new BigDecimal(source) : null;
        }
    }

    private static class StringToIntegerConverter extends BidirectionalConverter<String,Integer>{

        private StringToIntegerConverter(){}

        @Override
        public Integer convertTo(String source, Type<Integer> destinationType, MappingContext mappingContext) {
            Integer value = null;
            if(StringUtils.isNotBlank(source)){
                try {
                    value = Integer.valueOf(source);
                }catch (Exception e){

                }
            }
            return value;
        }

        @Override
        public String convertFrom(Integer source, Type<String> destinationType, MappingContext mappingContext) {
            return source == null ? null : String.valueOf(source);
        }
    }
}