package com.liu.controller;

import com.liu.service.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/18
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisService redisService;

    @RequestMapping("/set/{msg}")
    public void setMsg(@PathVariable("msg") String msg){
        String key = "name";
        redisService.setString(key, msg);
    }
    @RequestMapping("/append/{msg}")
    public void appendMsg(@PathVariable("msg") String msg){
        String key = "name";
        redisService.appendString(key,msg);
    }

    @RequestMapping("/get/{key}")
    public Object getMsg(@PathVariable("key") String key){
        return redisService.getString(key);
    }

    @RequestMapping("/setList")
    public void setList(){
        List<String> list =new ArrayList<>();
        list.add("java");
        list.add("c++");
        list.add("python");
        list.add("c#");
        redisService.setList("book",list);
    }
    @RequestMapping("/addList/{value}")
    public void addList(@PathVariable("value") String value){
        redisService.addList("book",value);
    }
    @RequestMapping("/getList/{name}")
    public Object getList(@PathVariable("name") String key){
      return   redisService.getList(key);
    }
    @RequestMapping("/getListOne/{name}")
    public Object getListOne(@PathVariable("name") String key){
      return   redisService.getListOne(key);
    }

    @RequestMapping("/publish/{msg}")
    public void publish(@PathVariable("msg") String msg){
        redisService.publish(msg);
    }
}
