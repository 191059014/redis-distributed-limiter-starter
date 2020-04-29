package com.hb.limiter.redis;

import com.hb.limiter.redis.model.RedisLimitParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;

/**
 * redis限流器
 *
 * @author Mr.huang
 * @since 2020/4/29 14:24
 */
public class RedisLimiter {

    /**
     * redis操作工具
     */
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 构造方法
     *
     * @param redisTemplate 字符串类型的redis操作工具
     */
    public RedisLimiter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * lua script
     */
    private static final String LUA_SCRIPT =
            " local key = KEYS[1] " +
                    " local maxTimes = tonumber(ARGV[1]) " +
                    " local expireTime = tonumber(ARGV[2]) " +
                    " local currentTimes = tonumber(redis.call('get', key) or '0')" +
                    " if currentTimes +1 > maxTimes " +
                    " then return current; " +
                    " end " +
                    " currentTimes = tonumber(redis.call('incr',key))" +
                    " if currentTimes == 1 then " +
                    " redis.call('expire',key,expireTime) " +
                    " end " +
                    " return currentTimes; ";

    /**
     * 是否限流通过
     *
     * @param key      限流的key
     * @param period   时间段
     * @param maxTimes 最大访问次数
     * @return true为通过，false为不通过
     */
    public boolean pass(String key, int period, int maxTimes) {
        return doLimit(new RedisLimitParameter(key, period, maxTimes)) <= maxTimes;
    }

    /**
     * 限流
     *
     * @param parameter 参数
     * @return 当前流量
     */
    public int doLimit(RedisLimitParameter parameter) {
        List<String> keys = Collections.singletonList(parameter.getKey());
        RedisScript<Integer> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Integer.class);
        return redisTemplate.execute(redisScript, keys, parameter.getMaxTimes() + "", parameter.getPeriod() + "");
    }

}
