package com.hb.limiter.redis.annotation;

import com.hb.limiter.redis.config.RedisLimiterAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启redis限流
 *
 * @author Mr.huang
 * @since 2020/4/29 14:24
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisLimiterAutoConfiguration.class})
public @interface EnableRedisLimiter {
}
