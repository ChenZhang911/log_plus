package com.jory.redislog.task;

import com.jory.redislog.service.WebSocketServer;
import com.tools.redis.log.logs.RedisBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.Set;


@Component
@EnableScheduling
public class MyScheduledTasks {

    @Resource
    private WebSocketServer webSocketServer;

    @Scheduled(fixedRate = 1000) // 每隔 5 秒执行一次
    public void task1() {
        webSocketServer.send();
    }


}
