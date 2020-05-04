package com.hb.limiter.redis.aop;

import com.hb.limiter.redis.RedisLimiter;
import com.hb.limiter.redis.annotation.RedisEasyLimiter;
import com.hb.limiter.redis.constant.RedisLimiterConstant;
import com.hb.limiter.redis.exception.RedisLimitException;
import com.hb.limiter.redis.model.RedisLimitParameter;
import com.hb.limiter.redis.util.RedisLimiterUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * redis限流切面
 *
 * @author Mr.huang
 * @since 2020/4/29 14:43
 */
@Aspect
public class RedisLimiterInterceptor {

    /**
     * the constant log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLimiterInterceptor.class);

    /**
     * redis限流器
     */
    private RedisLimiter redisLimiter;

    /**
     * 构造方法
     *
     * @param redisLimiter redis限流器
     */
    public RedisLimiterInterceptor(RedisLimiter redisLimiter) {
        this.redisLimiter = redisLimiter;
    }

    /**
     * 限流统一拦截
     *
     * @param pjp 方法执行上下文
     * @return 方法执行结果
     */
    @Around("execution(public * *(..)) && @annotation(com.hb.limiter.redis.annotation.RedisEasyLimiter)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedisEasyLimiter limitAnnotation = method.getAnnotation(RedisEasyLimiter.class);
        int period = limitAnnotation.period();
        int maxTimes = limitAnnotation.maxTimes();
        String key = limitAnnotation.key();

        int currentTimes = redisLimiter.doLimit(new RedisLimitParameter(key, period, maxTimes));
        if (currentTimes <= maxTimes) {
            return pjp.proceed();
        } else {
            String desc = "redis limit not pass" + RedisLimiterUtils.buildLimitErrorMessage(key, period, maxTimes, currentTimes);
            LOGGER.info(desc);
            throw new RedisLimitException(RedisLimiterConstant.REDIS_LIMIT_NOTPASS_KEY, desc);
        }

    }

}
