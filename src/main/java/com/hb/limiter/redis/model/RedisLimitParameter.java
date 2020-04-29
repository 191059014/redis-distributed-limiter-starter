package com.hb.limiter.redis.model;

import java.io.Serializable;

/**
 * redis限流参数模型
 *
 * @author Mr.huang
 * @since 2020/4/29 15:24
 */
public class RedisLimitParameter implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1595845976469337904L;

    /**
     * key
     */
    private String key;

    /**
     * 给定的时间范围 单位(秒)
     */
    private int period;

    /**
     * 一定时间内最多访问次数
     */
    private int maxTimes;

    public RedisLimitParameter(String key, int period, int maxTimes) {
        this.key = key;
        this.period = period;
        this.maxTimes = maxTimes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    @Override
    public String toString() {
        return "RedisLimitParameter{" +
                "key='" + key + '\'' +
                ", period=" + period +
                ", maxTimes=" + maxTimes +
                '}';
    }

}
