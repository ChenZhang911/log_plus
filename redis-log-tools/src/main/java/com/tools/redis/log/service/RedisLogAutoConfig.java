package com.tools.redis.log.service;

import com.tools.redis.log.logs.RedisBuilder;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @ClassName RedisLogService
 * @Description TODO
 * @Author 张晨
 * @Date 2023/6/1
 */

@Component
public class RedisLogAutoConfig {

    @PostConstruct
    public void init() {
//        利用spring注解，在启动时新建一个线程，用于回收，之前5s的日志，防止redis缓存过大
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    Jedis jedis = RedisBuilder.getJedis();
                    String logKey = RedisBuilder.getLogKey();
                    Long currentTime = System.currentTimeMillis();
                    //设置日志的key
                    String key = "logData:" + logKey;
                    // 获取最早到期的消息
                    Set<Tuple> expiredMessages = jedis.zrangeByScoreWithScores(key, 0, currentTime);
                    // 处理到期的消息
                    for (Tuple tuple : expiredMessages) {
                        String expiredMessage = tuple.getElement();
                        // 从延时队列中移除已过期的消息
                        jedis.zrem(key, expiredMessage);
                    }
//                    休眠1s
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // 启动线程
        thread.start();
    }
}
