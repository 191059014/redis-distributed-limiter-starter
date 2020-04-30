package com.hb.limiter.redis.util;

/**
 * 工具类
 *
 * @author Mr.huang
 * @since 2020/4/30 16:12
 */
public class RedisLimiterUtils {

    /**
     * 构建限流错误信息
     *
     * @param key          限流的key
     * @param period       过期时间
     * @param maxTimes     最大访问次数
     * @param currentTimes 当前访问次数
     * @return 错误信息
     */
    public static String buildLimitErrorMessage(String key, int period, int maxTimes, int currentTimes) {
        return new StringBuilder()
                .append("[")
                .append("key=").append(key).append("; ")
                .append("period=").append(period).append("; ")
                .append("maxTimes=").append(maxTimes).append("; ")
                .append("currentTimes=").append(currentTimes).append(";")
                .append("]")
                .toString();
    }

}
