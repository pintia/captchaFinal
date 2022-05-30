package cn.patest.captchafinal.utils;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 系统全局Cache接口，具体缓存方式需要实现该接口
 */
public interface IGlobalCache<K, V> {

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    boolean expire(K key, long time);

    /**
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(K key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(K key);

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    void del(K... key);

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    boolean set(K key, V value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    boolean set(K key, V value, long time);

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    long incr(K key, long delta);

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    long decr(K key, long delta);

    /**
     * 返回当前redisTemplate
     *
     * @return
     */
    RedisTemplate getRedisTemplate();
}
