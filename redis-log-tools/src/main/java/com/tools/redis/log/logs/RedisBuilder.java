package com.tools.redis.log.logs;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;


public class RedisBuilder {

    private static JedisPool jedisPool;

    @Getter
    private static String logKey;

    //单利模式
    private RedisBuilder() {}


    static {
        //获取配置信息
        Properties properties = PropertiesGetter.get("log-redis");
        //设置连接池参数
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));//最大的jedis实例
        config.setMaxWaitMillis(Integer.parseInt(properties.getProperty("maxWait")));//最大等待时间（毫秒）
        config.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));//借用时测试
        //redis链接
        jedisPool = new JedisPool(config, properties.getProperty("host")
                , Integer.parseInt(properties.getProperty("port"))
                , Integer.parseInt(properties.getProperty("timeout"))
//                ,  properties.getProperty("pass")
        );
        logKey = properties.getProperty("logKey");
    }

    public static synchronized Jedis getJedis(){
        Jedis resource = jedisPool.getResource();
        resource.select(0);
        return resource;
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}