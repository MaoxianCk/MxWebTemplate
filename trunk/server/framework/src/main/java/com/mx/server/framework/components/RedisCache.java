package com.mx.server.framework.components;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.server.framework.constant.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class RedisCache {
    private final RedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    public RedisCache(RedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        redisTemplate.setEnableTransactionSupport(false);
    }

    private String dealPrefix(String key) {
        return RedisConstants.REDIS_KEY_PREFIX + key;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        String _key = dealPrefix(key);
        if (value != null) {
            redisTemplate.opsForValue().set(_key, value);
            log.debug("redis set value, key:{}, value:{}", _key, value);
        } else {
            log.debug("redis no set value, key:{}", _key);
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        String _key = dealPrefix(key);
        redisTemplate.opsForValue().set(_key, value, timeout, timeUnit);
        log.debug("redis set value, key:{}, value:{}, timeout:{}, timeUnit:{}",
                _key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        String _key = dealPrefix(key);
        log.debug("redis set key expire, key:{}, timeout:{}", _key, timeout);
        return expire(_key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        String _key = dealPrefix(key);
        log.debug("redis set key expire, key:{}, timeout:{}, unit:{}", _key, timeout, unit);
        return Boolean.TRUE.equals(redisTemplate.expire(_key, timeout, unit));
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        String _key = dealPrefix(key);
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(_key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        String _key = dealPrefix(key);
        log.debug("redis delete key, key:{}", _key);
        return Boolean.TRUE.equals(redisTemplate.delete(_key));
    }


    /**
     * 根据前缀匹配删除多个key
     */
    public long deleteAllByKeyPrefix(final String keyPrefix) {
        String _key = dealPrefix(keyPrefix);
        log.debug("redis delete all by key prefix, key prefix: {}", _key);
        Set<String> keys = redisTemplate.keys(_key);
        return redisTemplate.delete(keys);
    }

    /**
     * 添加缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long rightPushCacheList(final String key, final List<T> dataList) {
        String _key = dealPrefix(key);
        Long count = redisTemplate.opsForList().rightPushAll(_key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        String _key = dealPrefix(key);
        return redisTemplate.opsForList().range(_key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        String _key = dealPrefix(key);
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(_key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        String _key = dealPrefix(key);
        return redisTemplate.opsForSet().members(_key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        String _key = dealPrefix(key);
        if (dataMap != null) {
            log.debug("redis set map: {}", _key);
            redisTemplate.opsForHash().putAll(_key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        String _key = dealPrefix(key);
        return redisTemplate.opsForHash().entries(_key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        String _key = dealPrefix(key);
        redisTemplate.opsForHash().put(_key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey, Class<T> clazz) {
        String _key = dealPrefix(key);
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return objectMapper.convertValue(opsForHash.get(_key, hKey), clazz);
    }
    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> List<T> getCacheMapListValue(final String key, final String hKey, Class<? extends T> clazz) {
        String _key = dealPrefix(key);
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        return objectMapper.convertValue(opsForHash.get(_key, hKey), type);
    }



    /**
     * 是否在map中存在
     */
    public boolean existsInMap(String key, String hKey) {
        String _key = dealPrefix(key);
        HashOperations op = redisTemplate.opsForHash();
        return op.hasKey(_key, hKey);
    }

}
