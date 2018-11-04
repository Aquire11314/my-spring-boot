package com.example.demo.listener;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class AyUserListener implements ServletContextListener {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AyUserService ayUserService;
    private static final String ALL_USER="ALL_USER_LIST";

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        System.out.println("------------servletcontext 初始化");
        List<AyUser> ayUserList=ayUserService.findAll();
        redisTemplate.delete(ALL_USER);
        redisTemplate.opsForList().leftPushAll(ALL_USER,ayUserList);
        List<AyUser>queryUserList=redisTemplate.opsForList().range(ALL_USER,0,-1);
        System.out.println("缓存中目前的用户数有"+queryUserList.size()+"人");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("------------servletcontext destroy");
    }
}
