package com.hb.limiter.redis.aop;

import com.hb.limiter.redis.RedisLimiter;
import com.hb.limiter.redis.annotation.DistributedLimiter;
import com.hb.limiter.redis.model.RedisLimitParameter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * redis限流切面
 *
 * @author Mr.huang
 * @since 2020/4/29 14:43
 */
@Aspect
@Configuration
public class RedisLimitInterceptor {

    /**
     * the constant log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLimitInterceptor.class);

    /**
     * redis限流器
     */
    private RedisLimiter redisLimiter;

    /**
     * 构造方法
     *
     * @param redisLimiter redis限流器
     */
    public RedisLimitInterceptor(RedisLimiter redisLimiter) {
        this.redisLimiter = redisLimiter;
    }

    /**
     * 限流统一拦截
     *
     * @param pjp 方法执行上下文
     * @return 方法执行结果
     */
    @Around("execution(public * *(..)) && @annotation(com.hb.limiter.redis.annotation.DistributedLimiter)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        DistributedLimiter limitAnnotation = method.getAnnotation(DistributedLimiter.class);
        int period = limitAnnotation.period();
        int maxTimes = limitAnnotation.maxTimes();
        String key = limitAnnotation.key();

        try {
            int count = redisLimiter.doLimit(new RedisLimitParameter(key, period, maxTimes));
            if (count <= maxTimes) {
                return pjp.proceed();
            } else {
                LOGGER.info("redis limit effect, key: {}, maxTimes: {}, count: {}", key, maxTimes, count);
                throw new RuntimeException("redis limit effect, you will be intercepted");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }

    }

}
