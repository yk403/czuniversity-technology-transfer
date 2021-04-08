package com.itts.common.utils.common;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author FULI
 */
@Component
public class RedisU {
    /**
     * 用于操作 redis
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 设置默认过期时间（一天）
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 指定过期时间
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean expire(String key, long timeout, TimeUnit unit){
        try {
            redisTemplate.expire(key, timeout, unit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
// ================================Object Set=================================
    /**
     *
     * @param string
     * @param object
     */
    public Boolean set(String string,Object object){
        try {
            redisTemplate.opsForValue().set(string, JSONUtil.toJsonStr(object),DEFAULT_EXPIRE,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Boolean set(String string,String tring){
        try {
            redisTemplate.opsForValue().set(string,tring,DEFAULT_EXPIRE,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Boolean set(String string,Object object,long timeout, TimeUnit unit){
        try {
            redisTemplate.opsForValue().set(string, JSONUtil.toJsonStr(object),timeout,unit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean getset(String string,Object object){
        try {
            redisTemplate.opsForValue().getAndSet(string,object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

// ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time,TimeUnit unit) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time,unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincrement(String key, String item, double by) {
        if (by < 0) {
            throw new RuntimeException("请输入正数");
        }
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecrement(String key, String item, double by) {
        if (by < 0) {
            throw new RuntimeException("请输入正数");
        }
        return redisTemplate.opsForHash().increment(key, item, -by);
    }



// ================================自增自减=================================
    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("请输入正数");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

// ================================Get=================================
    /**
     *
     * @param string
     * @param TT
     * @param <T>
     * @return
     */
    public <T> T getBean(String string, Class<T> TT){
        Object o = redisTemplate.opsForValue().get(string);
        T t = JSONUtil.toBean(o.toString(), TT);
        return t;
    }
    public String getString(String string){
        Object o = redisTemplate.opsForValue().get(string);
        String s = o.toString();
        return s;
    }

}
