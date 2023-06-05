# log_plus

使用logback+redis+springboot+thymeleaf，实现网页查看服务日志

# redis-log-tools

这个项目重写logback的AppenderBase
并使用了redis的zset数据类型，实现消息队列（定时过期防止redis出现bigkey的问题）。
大多数javaWeb项目都是SpringBoot，为了方便项目集成，特自定义了一个starter。
采用@PostConstruct注释，在项目启动后，会建一个线程，用于监听过期的日志，并移除
如何使用:
项目添加依赖
```
  <dependency>
      <groupId>com.redis.log</groupId>
      <artifactId>redis-log-tools</artifactId>
      <version>0.0.1-SNAPSHOT</version>
  </dependency>
```

在项目
resource下新增配置文件
log-redis.properties
内容如下：
```
maxIdle=100
maxWait=3000
testOnBorrow=true

host=127.0.0.1
port=6379
timeout=3000
pass=
logKey=服务名称
```

# redislog

这个是网页查看日志服务
使用Springboot+websocket+thymeleaf，
Spring定时任务，实现实时查看日志效果
配置好log-redis.properties
启动项目，在监听服务输入你要监听的服务
并可以过滤出关字
