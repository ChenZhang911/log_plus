package com.tools.redis.log.logs;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import redis.clients.jedis.Jedis;

/**
 * Created by yuyu on 2018/3/15.
 * 自定义日志处理
 */
public class RedisAppender extends AppenderBase<LoggingEvent> {

    @Override
    protected void append(LoggingEvent loggingEvent) {
        //获取日志数据
        LogData logData = new LogData(loggingEvent);
        //设置日志保存数据库
        Jedis jedis = RedisBuilder.getJedis();
        String logKey = RedisBuilder.getLogKey();
        //设置日志的key
        String key = "logData:" + logKey;
        long currentTime = System.currentTimeMillis();
//        日志 过期时间
        long delay = 5000; // 延时5秒
        long delayedTime = currentTime + delay;
//      使用redis 的zset
        jedis.zadd(key, delayedTime, JsonBuilder.getString(logData));
        RedisBuilder.closeJedis(jedis);

    }
}