package com.hb.limiter.redis.config;

import com.hb.limiter.redis.RedisLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis限流自动配置
 *
 * @author Mr.huang
 * @since 2020/4/29 14:34
 */
@Configuration
@ConditionalOnClass(StringRedisTemplate.class)
public class RedisLimiterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 1)
    public RedisLimiter redisLimiter(StringRedisTemplate stringRedisTemplate) {
        return new RedisLimiter(stringRedisTemplate);
    }

}
