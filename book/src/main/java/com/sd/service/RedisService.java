package com.sd.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 基于{@link RedisTemplate} 的操作类
 * @Package: com.yixin.redis.T
 * @Author Yao.Jie
 * @date 2019/9/23 10:22
 */
@Slf4j
@Component
public class RedisService {

    /** 标识 **/
    public static final String SIGN_ASTERISK = "*";

    /**
     * 参数不正常时返回的值
     */
    public static final Long ERROR_VALUE = -1L;
    /**
     * 参数不正常时返回的值
     */
    public static final double ERROR_DOUBLE = -1D;

    /** 默认超时时间 **/
    public static final long DEFAULT_TIMEOUT = 30L;

    /** 默认超时时间单位 **/
    public static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

    /*************** keys相关操作 *********************/

    private RedisTemplate<String, Object> redisTemplate;

    /**
      * @Description: 构造类注入 redisTemplate
      * @param redisTemplate :
      * @return
      * @throws
      * @author Yao.Jie
      * @date 2019/9/23 10:25
       */
    @Autowired
    public RedisService(RedisTemplate redisTemplate){

        this.redisTemplate = redisTemplate;
        // 为解决Lettuce的Connection Rest peer的问题，需要开验证链接功能,有对TPS有些影响，但与影响业务相比可以忽略
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (factory instanceof LettuceConnectionFactory) {
            ((LettuceConnectionFactory)factory).setValidateConnection(true);
        }
    }

    /**
     * @Description: 序列化{@code key}
     * @param key :
     * @return : byte[]
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public byte[] dump(String key) {
        if (StringUtils.isBlank(key)) {
            return new byte[0];
        }
        return redisTemplate.dump(key);
    }

    /**
     * @Description: 修改key的名称
     * @param oldName
     * @param newName
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void rename(String oldName, String newName) {
        if (StringUtils.isBlank(oldName) || StringUtils.isBlank(newName)) {
            return;
        }
        redisTemplate.rename(oldName, newName);

    }

    /**
     * @Description: 仅当newName不存时是，将oldName修改为newName
     * @param oldName
     * @param newName
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void renameIfAbsent(String oldName, String newName) {
        if (StringUtils.isBlank(oldName) || StringUtils.isBlank(newName)) {
            return;
        }
        redisTemplate.renameIfAbsent(oldName, newName);
    }

    /**
     * @Description: key 存在时删除 key
     * @param key
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void delete(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        redisTemplate.delete(key);
    }

    /**
     * @Description: 批量删除
     * @param keys
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void delete(Collection keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        redisTemplate.delete(keys);
    }

    /**
     * @Description: 检查给定 key 是否存在
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean hasKey(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * @Description: 设置过期时间
     * @param key
     * @param timeout 不能小于{@code 0}，不能大于{@link Long#MAX_VALUE} 秒
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean expire(String key, long timeout) {

        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (timeout <= 0 || timeout > Long.MAX_VALUE) {
            timeout = DEFAULT_TIMEOUT;
        }
        return expire(key, timeout, DEFAULT_TIMEUNIT);
    }

    /**
     * @Description: 设置过期时间带单位
     * @param key
     * @param timeout 不能小于{@code 0}，不能大于{@link Long#MAX_VALUE}
     * @param unit {@link TimeUnit}
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {

        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (timeout <= 0 || timeout > Long.MAX_VALUE) {
            timeout = DEFAULT_TIMEOUT;
        }
        if (unit == null) {
            unit = TimeUnit.SECONDS;
        }
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * @Description: 查找所有符合给定模式 {@param pattern} 的 {@code key}
     * @param pattern 不能是{@code *}
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<String> keys(String pattern) {
        Set<String> keys = new HashSet();
        if (StringUtils.isBlank(pattern) || SIGN_ASTERISK.equals(pattern.trim())) {
            return keys;
        }
        keys = redisTemplate.keys(pattern);
        return keys;
    }

    /**
     * @Description: 返回 key 的剩余的过期时间
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long getExpire(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return getExpire(key, DEFAULT_TIMEUNIT);
    }

    /**
     * @Description: 返回 key 的剩余的过期时间
     * @param key
     * @param unit
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long getExpire(String key, TimeUnit unit) {

        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        if (unit == null) {
            unit = DEFAULT_TIMEUNIT;
        }
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * @Description: 移除 key 的过期时间，key 将持久保持
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean persist(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return redisTemplate.persist(key);
    }

    /**
     * @Description: 返回 key 所储存的值的类型
     * @param key
     * @return 如果 key 为 {@code null} 返回 {@code null}
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public DataType type(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return redisTemplate.type(key);
    }

    /******************** String相关操作 ***********************/

    /**
     * @Description: 设置指定key的值
     * @param key
     * @param value
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void set(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @Description: 用value参数覆盖给定key所存储的字符串的值，从偏移量offset开始
     * @param key
     * @param value
     * @param offset 从指定位置开始覆写
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void setRange(String key, Object value, long offset) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取指定 key 的值
     * 
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @Description: 获取指定的字符串
     * @param key
     * @param start
     * @param end
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object getRange(String key, long start, long end) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * @Description: 将给定 key 的值设为 value ，并返回key 的旧值(old value)
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object getAndSet(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * @Description: 批量获取
     * @param keys
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public List<Object> multiGet(Collection keys) {
        List<Object> resultList = new ArrayList();
        if (CollectionUtils.isEmpty(keys)) {
            return resultList;
        }
        resultList = redisTemplate.opsForValue().multiGet(keys);
        return resultList;
    }

    /**
     * @Description: 设置过期时间
     * @param key :
     * @param value :
     * @return : void
     * @throws @author hanpeng
     * @date 2018/11/28 9:46
     */
    public void setEx(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        setEx(key, value, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    /**
     * @Description: 设置过期时间
     * @param key
     * @param value
     * @param timeout 秒
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void setEx(String key, Object value, long timeout) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (timeout < 0 || timeout > Long.MAX_VALUE) {
            timeout = DEFAULT_TIMEOUT;
        }
        setEx(key, value, timeout, DEFAULT_TIMEUNIT);
    }

    /**
     * @Description: 设置过期时间
     * @param key
     * @param value
     * @param timeout 不能小于 {@code 0} 不能大于{@link Long#MAX_VALUE}
     * @param unit 不能为 {@code null}
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void setEx(String key, Object value, long timeout, TimeUnit unit) {

        if (StringUtils.isBlank(key) || timeout < 0 || timeout > Long.MAX_VALUE || unit == null) {
            return;
        }
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * @Description: 只有在 key 不存在时设置 key 的值
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean setIfAbsent(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * @Description: 只有在 key 不存在时设置 key 的值 ,过期时间
     * @param key :
     * @param value :
     * @param timeOut :
     * @param unit :
     * @return : boolean
     * @throws @author hanpeng
     * @date 2018/11/28 10:07
     */
    public boolean setIfAbsent(String key, Object value, long timeOut, TimeUnit unit) {
        boolean result = false;
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForValue().setIfAbsent(key, value);
        expire(key, timeOut, unit);
        return result;
    }

    /**
     * @Description: 为多个键分别设置它们的值
     * @param maps
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void multiSet(Map<String, Object> maps) {
        if (CollectionUtils.isEmpty(maps)) {
            return;
        }
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * @Description: 同时设置一个或多个 key-value 对，当且仅 当所有给定 key 都不存在
     * @param maps
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean multiSetIfAbsent(Map<String, Object> maps) {
        if (CollectionUtils.isEmpty(maps)) {
            return false;
        }
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * @Description: 追加到末尾
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public int append(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * @Description: 增加(自增长), 负数则为自减
     * @param key
     * @param increment
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long incrBy(String key, long increment) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @Description: 获取字符串的长度
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long size(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForValue().size(key);
    }

    /**************** Hash 相关操作 *******************/

    /**
     * @Description: 获取所有给定字段的值
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Map<Object, Object> hGetAll(String key) {
        Map<Object, Object> result = new HashMap();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForHash().entries(key);
        return result;
    }

    /**
     * @Description: 获取存储在哈希表中指定字段的值
     * @param key
     * @param hashKey
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object hGet(String key, Object hashKey) {
        if (StringUtils.isBlank(key) || hashKey == null || StringUtils.isBlank(hashKey.toString())) {
            return null;
        }
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * @Description: 迭代哈希表中的键值对
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Map<Object, Object> hScan(String key) {
        Map<Object, Object> result = new HashMap();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        Map.Entry<Object, Object> entry = null;
        while (curosr.hasNext()) {
            entry = curosr.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * @Description: 获取所有给定字段的值
     * @param key
     * @param fields
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(fields)) {
            return new ArrayList();
        }
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * @Description: 添加字段
     * @param key
     * @param hashKey
     * @param value
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void hPut(String key, Object hashKey, Object value) {
        if (StringUtils.isBlank(key) || hashKey == null || StringUtils.isBlank(hashKey.toString())) {
            return;
        }
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * @Description: 添加多个字段
     * @param key
     * @param maps
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void hPutAll(String key, Map maps) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(maps)) {
            return;
        }
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * @Description: 仅当hashKey不存在时才设置
     * @param key
     * @param hashKey
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean hPutIfAbsent(String key, Object hashKey, Object value) {
        if (StringUtils.isBlank(key) || hashKey == null || StringUtils.isBlank(hashKey.toString())) {
            return false;
        }
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * @Description: 删除一个或多个哈希表字段
     * @param key
     * @param fields
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long hDelete(String key, Object... fields) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(fields)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * @Description: 查看哈希表 key 中，指定的字段是 否存在
     * @param key
     * @param field
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean hExists(String key, Object field) {
        if (StringUtils.isBlank(key) || field == null || StringUtils.isBlank(field.toString())) {
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * @Description: 获取所有哈希表中的字段
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> hKeys(String key) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForHash().keys(key);
        return result;
    }

    /**
     * @Description: 获取哈希表中字段的数量
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long hSize(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * @Description: 获取哈希表中所有值
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public List<Object> hValues(String key) {
        List<Object> result = new ArrayList();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForHash().values(key);
        return result;
    }

    /************ list相关操作 **************/

    /**
     * @Description: 存储在list头部
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lLeftPush(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @Description: 如果pivot存在,再pivot前面添加
     * @param key
     * @param pivot
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lLeftPush(String key, Object pivot, Object value) {
        if (StringUtils.isBlank(key) || pivot == null || StringUtils.isBlank(pivot.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * @Description: 存储在list头部
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lLeftPushAll(String key, Object... value) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(value)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * @Description: 存储在list头部
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lLeftPushAll(String key, Collection<Object> value) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(value)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * @Description: 当list存在的时候才加入
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lLeftPushIfPresent(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * @Description: 存储在list尾部
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lRightPush(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @Description: 在pivot元素的右边添加值
     * @param key
     * @param pivot
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lRightPush(String key, Object pivot, Object value) {
        if (StringUtils.isBlank(key) || pivot == null || StringUtils.isBlank(pivot.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * @Description: 存储在list尾部
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lRightPushAll(String key, Object... value) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(value)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @Description:
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lRightPushAll(String key, Collection<Object> value) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(value)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @Description: 当list存在的时候才加入
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lRightPushIfPresent(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * @Description: 通过索引设置列表元素的值
     * @param key
     * @param index
     * @param value
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void lSet(String key, long index, Object value) {
        if (StringUtils.isBlank(key) || index < 0 || index > Long.MAX_VALUE || value == null
            || StringUtils.isBlank(value.toString())) {
            return;
        }
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * @Description: 通过索引获取列表中的元素
     * @param key
     * @param index
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object lIndex(String key, long index) {
        if (StringUtils.isBlank(key) || index < 0 || index > Long.MAX_VALUE) {
            return null;
        }
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * @Description: 获取列表指定范围内的元素
     * @param key
     * @param start
     * @param end
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public List<Object> lRange(String key, long start, long end) {
        List<Object> result = new ArrayList();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForList().range(key, start, end);
        return result;
    }

    /**
     * @Description: 移出并获取列表的第一个元素
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object lLeftPop(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @Description: 移出并获取列表的第一个元素， 如果列 表没有元素会阻塞列表直到等待超时或 发现可弹出元素为止
     * @param key
     * @param timeout
     * @param unit
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object lBLeftPop(String key, long timeout, TimeUnit unit) {

        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (timeout <= 0 || timeout > Long.MAX_VALUE) {
            timeout = DEFAULT_TIMEOUT;
        }
        if (unit == null) {
            unit = TimeUnit.MICROSECONDS;
        }
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * @Description: 移除并获取列表最后一个元素
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object lRightPop(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @Description: 移出并获取列表的最后一个元素， 如 果列表没有元素会阻塞列表直到等待超时 或发现可弹出元素为止
     * @param key
     * @param timeout
     * @param unit
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Object lBRightPop(String key, long timeout, TimeUnit unit) {

        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (timeout <= 0 || timeout > Long.MAX_VALUE) {
            timeout = DEFAULT_TIMEOUT;
        }
        if (unit == null) {
            unit = DEFAULT_TIMEUNIT;
        }
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * @Description: 删除集合中值等于value得元素
     * @param key
     * @param index
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lRemove(String key, long index, Object value) {
        if (StringUtils.isBlank(key) || index < 0 || index > Long.MAX_VALUE) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * @Description: 裁剪list
     * @param key
     * @param start
     * @param end
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void lTrim(String key, long start, long end) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * @Description: 获取列表长度
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long lLen(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForList().size(key);
    }

    /** ------------set 相关操作----------------- */
    /**
     * @Description: 添加
     * @param key
     * @param values
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    long sAdd(String key, Object... values) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(values)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * @Description: 获取集合所有元素
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sMembers(String key) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForSet().members(key);
        return result;
    }

    /**
     * @Description: 获取集合大小
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long sSize(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * @Description: 判断集合是否包含value
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean sIsMember(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * @Description: 获取两个集合的交集
     * @param key
     * @param otherKey
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sIntersect(String key, String otherKey) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key) || StringUtils.isBlank(otherKey)) {
            return result;
        }
        result = redisTemplate.opsForSet().intersect(key, otherKey);
        return result;
    }

    /**
     * @Description: 获取key集合与多个集合的交集
     * @param key
     * @param otherKeys
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sIntersect(String key, Collection<String> otherKeys) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(otherKeys)) {
            return result;
        }
        result = redisTemplate.opsForSet().intersect(key, otherKeys);
        return result;
    }

    /**
     * @Description:
     * @param key
     * @param otherKeys
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sUnion(String key, String otherKeys) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key) || StringUtils.isBlank(otherKeys)) {
            return result;
        }
        result = redisTemplate.opsForSet().union(key, otherKeys);
        return result;
    }

    /**
     * @Description: 获取key集合与多个集合的并集
     * @param key
     * @param otherKeys
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sUnion(String key, Collection<String> otherKeys) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(otherKeys)) {
            return result;
        }
        result = redisTemplate.opsForSet().union(key, otherKeys);
        return result;
    }

    /**
     * @Description: 获取两个集合的差集
     * @param key
     * @param otherKey
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sDifference(String key, String otherKey) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key) || StringUtils.isBlank(otherKey)) {
            return result;
        }
        result = redisTemplate.opsForSet().difference(key, otherKey);
        return result;
    }

    /**
     * @Description: 获取key集合与多个集合的差集
     * @param key
     * @param otherKeys
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sDifference(String key, Collection<String> otherKeys) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(otherKeys)) {
            return result;
        }
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * @Description: 移除
     * @param key
     * @param values
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long sRemove(String key, Object... values) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(values)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * @Description: 遍历
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> sScan(String key) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        Cursor<Object> curosr = redisTemplate.opsForSet().scan(key, ScanOptions.NONE);
        while (curosr.hasNext()) {
            result.add(curosr.next());
        }
        return result;
    }

    /** ---------------zset 相关操作----------------- */

    /**
     * @Description: 添加元素,有序集合是按照元素的score值由小到大排列
     * @param key
     * @param value
     * @param score
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public boolean zAdd(String key, Object value, double score) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return false;
        }
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @Description: 获取集合的元素, 从小到大排序, start开始位置, end结束位置
     * @param key
     * @param start
     * @param end
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> zRange(String key, long start, long end) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForZSet().range(key, start, end);
        return result;
    }

    /**
     * @Description: 根据Score值查询集合元素的值, 从小到大排序
     * @param key
     * @param min
     * @param max
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> zRangeByScore(String key, double min, double max) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        result = redisTemplate.opsForZSet().rangeByScore(key, min, max);
        return result;
    }

    /**
     * @Description: 获取集合的元素, 从大到小排序
     * @param key
     * @param start
     * @param end
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> zReverseRange(String key, long start, long end) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * @Description: 根据Score值查询集合元素, 从大到小排序
     * @param key
     * @param min
     * @param max
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> zReverseRangeByScore(String key, double min, double max) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * @Description: 根据Score值查询集合元素, 从大到小排序
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Set<Object> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        Set<Object> result = new HashSet();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * @Description: 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zRank(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * @Description: 返回元素在集合的排名,按元素的score值由大到小排列
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zReverseRank(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * @Description: 根据score值获取集合元素数量
     * @param key
     * @param min
     * @param max
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zCount(String key, double min, double max) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * @Description: 获取集合大小
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zSize(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * @Description: 获取集合大小
     * @param key
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zZCard(String key) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * @Description: 获取集合中value元素的score值
     * @param key
     * @param value
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public double zScore(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())) {
            return ERROR_DOUBLE;
        }
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * @Description: 移除
     * @param key
     * @param values
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    long zRemove(String key, Object... values) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(values)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * @Description: 增加元素的score值，并返回增加后的值
     * @param key
     * @param value
     * @param delta
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public double zIncrementScore(String key, String value, double delta) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return ERROR_DOUBLE;
        }
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * @Description: 移除指定索引位置的成员
     * @param key
     * @param start
     * @param end
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zRemoveRange(String key, long start, long end) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * @Description: 根据指定的score值的范围来移除成员
     * @param key
     * @param min
     * @param max
     * @return
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public long zRemoveRangeByScore(String key, double min, double max) {
        if (StringUtils.isBlank(key)) {
            return ERROR_VALUE;
        }
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * @Description: 遍历
     * @param key
     * @return 带score
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public Map<Object, Object> zScan(String key) {
        Map<Object, Object> result = new HashMap();
        if (StringUtils.isBlank(key)) {
            return result;
        }
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            result.put(item.getValue(), item.getScore());
        }
        return result;
    }

    /**
     * @Description: 发布消息
     * @param channel
     * @param message
     * @throws @author hanpeng
     * @date 2018/11/14 16:18
     */
    public void publish(String channel, Object message) {
        if (StringUtils.isBlank(channel)) {
            return;
        }
        redisTemplate.convertAndSend(channel, message);
    }

}
