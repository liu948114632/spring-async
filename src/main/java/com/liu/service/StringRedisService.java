package com.liu.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/18
 */
@Service
public class StringRedisService {
    private final static Logger LOGGER = Logger.getLogger(StringRedisService.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RedisTemplate redisTemplate;

    //注入之前进行操作。设置一些默认值。
    @PostConstruct
    public void init(){
        StringRedisSerializer serializer = new StringRedisSerializer();
        //该方法是设置redis中的基本格式。此时的redisTemplate类型与stringRedisTemplate一样。只不过是两个对象
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
    }

    /**
     * redis设置值，覆盖原来的值。如果没有该key，新建
     * @param key
     * @param value
     */
    public void setString(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void appendString(String key, String value){
        stringRedisTemplate.opsForValue().append(key,value);
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 修改list
     * @param key
     * @param list
     */
    public void setList(String key, List<String> list){
        stringRedisTemplate.delete(key);
        stringRedisTemplate.opsForList().leftPushAll(key,list);
    }

    /**
     * 在原来list中添加数据
     * @param key
     * @param value
     */
    public void addList(String key, String value){
        stringRedisTemplate.opsForList().leftPush(key,value);
    }

    /**
     * 获取list
     * @param key
     * @return
     */
    public List<String> getList(String key){
        return stringRedisTemplate.opsForList().range(key,0,stringRedisTemplate.opsForList().size(key));
    }

    /**
     *
     * 获取其中一个。这里的方法直接出栈。去除后直接删除了。如果是读取，要使用range或者index
     * @param key
     * @return
     */
    public String getListOne(String key){
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    /**
     * 发送
     * @param message
     */
    public void publish(String channel ,String message){
        stringRedisTemplate.convertAndSend(channel,message);
        LOGGER.info("使用redis消息中间件发送消息");
    }

    public void subscribe(String channel){
        executorService.execute(() -> stringRedisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) {
                connection.subscribe((message, bytes) -> LOGGER.info(message.toString()), channel.getBytes());
                return null;
            }
        }));
    }
}
