package com.spring.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * Redis存储服务
 * @param <K>
 * @param <V>
 * @author chenhaiyan
 */
@Service("redisService")
public class RedisService<K, V> {

    @Autowired(required = false)
    @Qualifier("redisTemplate")
    private RedisTemplate<K, V> redisTemplate;

    /**
     * @param key
     * @param value
     * @param minutesToLive 超时时间（分钟）
     * @author chenhaiyan
     */
    public void put(final K key, final V value, long timeout, TimeUnit unit) {
        ValueOperations<K, V> ops = redisTemplate.opsForValue();
        ops.set(key, value, timeout, unit);
    }

    /**
     * @param key
     * @param value
     * @param minutesToLive 超时时间（分钟）
     * @author chenhaiyan
     */
    public void put(final K key, final V value, long minutesToLive) {
        ValueOperations<K, V> ops = redisTemplate.opsForValue();
        ops.set(key, value, minutesToLive, TimeUnit.MINUTES);
    }

    /**
     * zAdd(向Key这个set添加元素，按照时间戳排序，timeout时间后超时) @Title zAdd @author
     * xin.song @return void 返回类型 @throws
     */
    public void zAdd(final K key, final V value, long timeout, TimeUnit unit) {
        ZSetOperations<K, V> ops = redisTemplate.opsForZSet();
        ops.add(key, value, System.currentTimeMillis());
        ops.getOperations().expire(key, timeout, unit);
    }

    public Set<V> zGet(final K key, long start, long end) {
        ZSetOperations<K, V> ops = redisTemplate.opsForZSet();
        return ops.range(key, start, end);
    }

    public Set<V> zGetByScore(final K key, long start, long end) {
        ZSetOperations<K, V> ops = redisTemplate.opsForZSet();
        return ops.rangeByScore(key, start, end);
    }

    /**
     * @param key
     * @param value
     * @author chenhaiyan
     */
    public void put(final K key, final V value) {
        ValueOperations<K, V> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public V getSet(final K key, final V value) {
        return redisTemplate.execute(new RedisCallback<V>() {
            @SuppressWarnings("unchecked")
            public V doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
                byte[] keys = keySerializer.serialize(key);
                RedisSerializer<V> valueSerializer = (RedisSerializer<V>) redisTemplate.getValueSerializer();
                byte[] values = valueSerializer.serialize(value);
                byte[] old = connection.getSet(keys, values);
                return valueSerializer.deserialize(old);
            }
        });
    }

    /**
     * @return
     * @author chenhaiyan
     */
    public RedisConnectionFactory getRedisConnectionFactory() {
        return redisTemplate.getConnectionFactory();
    }

    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        ValueOperations<K, V> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public boolean expire(final K key, final long timeout, final TimeUnit unit) {
        if (key == null) {
            return false;
        }
        return redisTemplate.expire(key, timeout, unit);
    }

    public void remove(final K key) {
        if (key != null) {
            redisTemplate.delete(key);
        }
    }

    public Set<K> getKeys(K pattern) {
        Set<K> set = null;
        if (pattern != null) {
            set = redisTemplate.keys(pattern);
        }
        return set;
    }

    public long increment(K key, long delta) {
        ValueOperations<K, V> ops = redisTemplate.opsForValue();
        return ops.increment(key, delta);
    }

    public boolean setNX(final K key, final V value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @SuppressWarnings("unchecked")
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
                byte[] keys = keySerializer.serialize(key);
                RedisSerializer<V> valueSerializer = (RedisSerializer<V>) redisTemplate.getValueSerializer();
                byte[] values = valueSerializer.serialize(value);
                return connection.setNX(keys, values);
            }
        });
    }

    /**
     *  新增到hmap
     * @author zhen.he
     * @Description:Redis HMSET
     * @author 00013519
     * @param key
     * @param hashKey
     * @param value
     */
    public void hput(K key, Object hashKey, V value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }


    /**
     * 删除hmap中的对象
     * @author zhen.he
     * @Description:Redis HDEL
     * @author 00013519
     * @param key
     * @param hashKey
     */
    public void hdel(K key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }


    /**
     * 从hmap中获取对象
     * @author zhen.he
     * @Description:
     * @author 00013519
     * @param key
     * @param hashKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public V hget(K key, Object hashKey) {
        return (V) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 从map中获取多个对象
     * @author zhen.he
     * @Description:
     * @param key
     * @param hashKeys
     * @return
     */
    public List<Object> multiGet(K key, Collection<Object> hashKeys) {

        return redisTemplate.opsForHash().multiGet(key, hashKeys); 
    }


    /**
     * 判断key是否存在
     * @author zhen.he
     * @Description:
     * @author 00013519
     * @param key
     * @return
     */
    public Boolean hasKey(K key){
    	return redisTemplate.hasKey(key);
    }

    public Map<Object, Object> hEntry(K key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Long leftPush(K key, V value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Long rightPush(K key, V value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public V rightPop(K key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    // 获取start到end之间的元素
    public List<V> range(K key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Long lRemove(K key, long count, V value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    // 删除start和end之外的元素
    public void trimList(K key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    // 获取list长度
    public Long listSize(K key) {
        return redisTemplate.opsForList().size(key);
    }

    public Long setSize(K key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 获取过期时间
     * @author zhen.he
     * @Description:
     * @param key
     * @return
     */
    public Long getExpire(K key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 获取过期时间
     * @author zhen.he
     * @Description:
     * @param key
     * @param timeunit
     * @return
     */
    public Long getExpire(K key,TimeUnit timeunit){
        return redisTemplate.getExpire(key,timeunit);
    }
}
