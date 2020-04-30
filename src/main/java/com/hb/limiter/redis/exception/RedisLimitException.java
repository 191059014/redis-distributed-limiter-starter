package com.hb.limiter.redis.exception;

/**
 * redis限流异常
 *
 * @author Mr.huang
 * @since 2020/4/30 15:59
 */
public class RedisLimitException extends RuntimeException {

    /**
     * 错误标识
     */
    private String key;
    /**
     * 错误描述
     */
    private String desc;

    public RedisLimitException(String key, String desc) {
        super(key);
        this.key = key;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "RedisLimitException{" +
                "key='" + key + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

}
