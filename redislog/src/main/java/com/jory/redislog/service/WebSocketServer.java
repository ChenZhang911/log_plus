package com.jory.redislog.service;

import cn.hutool.core.util.StrUtil;
import com.tools.redis.log.logs.RedisBuilder;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint("/webSocket")
@Component
public class WebSocketServer {
	 //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<Session, String> sessionPools = new ConcurrentHashMap<>();

    public void  send(){
        Jedis jedis = RedisBuilder.getJedis();
        sessionPools.forEach((session,v)->{
            try {
                if (StrUtil.isNotEmpty(v)){
                    Long currentTime = System.currentTimeMillis();
                    //设置日志的key
                    String redisKey = "logData:"+v;
                    // 获取最早到期的消息
                    Set<Tuple> expiredMessages = jedis.zrangeByScoreWithScores(redisKey, 0, currentTime);
                    // 处理到期的消息
                    for (Tuple tuple : expiredMessages) {
                        String expiredMessage = tuple.getElement();
                        jedis.zrem(redisKey, expiredMessage);
                        session.getBasicRemote().sendText(expiredMessage);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        RedisBuilder.closeJedis(jedis);
    }
    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session){
        sessionPools.put(session,"");
        System.out.println(  "加入webSocket！当前人数为" + onlineNum);
    }

    //关闭连接时调用
    @OnClose
    public void onClose(Session session,@PathParam(value = "username") String userName){
        sessionPools.remove(session);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息后，根据接收人的username把消息推下去或者群发
    // to=-1群发消息
    @OnMessage
    public void onMessage(String message,Session session) throws IOException{
        String id = session.getId();
        System.out.println("server"+ id +" get" + message);
        sessionPools.put(session,message);
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
    
    public static AtomicInteger getOnlineNumber() {
        return onlineNum;
    }
    
    public static ConcurrentHashMap<Session,String> getSessionPools() {
        return sessionPools;
    }
}
