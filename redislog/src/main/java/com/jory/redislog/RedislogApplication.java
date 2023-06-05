package com.jory.redislog;

import com.tools.redis.log.service.RedisLogAutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = RedisLogAutoConfig.class)
@EnableScheduling
public class RedislogApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedislogApplication.class, args);
    }

}
