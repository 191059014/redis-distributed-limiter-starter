# redis-distributed-limiter-starter
自定义的redis+lua分布式限流组件
## 引入方法
```
首先下载项目，然后依赖pom文件，在启动类上加上注解@EnableRedisLimiter，最后根据场景决定使用注解的方式还是注入的方式。
```
- 依赖pom文件
```
<dependency>
   <groupId>com.hb</groupId>
   <artifactId>redis-distributed-limiter-starter</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</dependency>
```
- 注解方式
```
@GetMapping("/testDistributedLimiter")
@RedisEasyLimiter(key = "distributedLimiter", period = 10, maxTimes = 3)
public void testDistributedLimiter() {
    System.out.println("限流通过");
}
```
- 注入方式
```
@Autowired
private RedisLimiter redisLimiter;

@GetMapping("/distributedLimiter")
public void testDistributedLimiter() {
    if (!redisLimiter.pass("distributedLimiter", 10, 3)) {
        System.out.println("限流不通过");
        return;
    }
    System.out.println("限流通过");
}
```