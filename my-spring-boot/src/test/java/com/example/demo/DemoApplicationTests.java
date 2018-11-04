package com.example.demo;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private AyUserService ayUserService;

    @Test
    public void contextLoads() {

    }

    @Test
    public void mySqlTest(){
        String sql="select id,name,password from ay_user";
        List<AyUser> list=jdbcTemplate.query(sql, new RowMapper<AyUser>() {
            @Override
            public AyUser mapRow(ResultSet resultSet, int i) throws SQLException {
                AyUser user=new AyUser();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        System.out.println("查询成功");
        for (AyUser user:list
             ) {
            System.out.println(user);
        }
    }

    @Test
    public void testUserService(){
        List<AyUser>userList=ayUserService.findAll();
        System.out.println("findALL():"+userList.size());

        List<AyUser>userList2=ayUserService.findByName("t1");
        System.out.println("findByName():"+userList2.size());
        Assert.isTrue(userList2.get(0).getName().equals("t1"),"data error");

        List<AyUser>userList3=ayUserService.findByNameLike("%t%");
        System.out.println("findByNameLike():"+userList3.size());
        Assert.isTrue(userList3.get(0).getName().equals("t1"),"data error");

        List<String> ids=new ArrayList<>();
        ids.add("1");
        ids.add("2");
        List<AyUser>userList4=ayUserService.findByIdIn(ids);
        System.out.println("findByIdIn():"+userList4.size());
        Assert.isTrue(userList4.get(0).getName().equals("t1"),"data error");

        PageRequest pageRequest=new PageRequest(0,10);
        Page<AyUser> userlist5=ayUserService.findAll(pageRequest);
        System.out.println("findAll():"+ userlist5.getTotalPages()+"/"+userlist5.getSize());


        AyUser ayUser=new AyUser();
        ayUser.setId("3");
        ayUser.setName("t3");
        ayUser.setPassword("123");
        ayUserService.save(ayUser);

        ayUserService.delete("3");


    }

    @Test
    public void testTransaction(){
        AyUser ayUser=new AyUser();
        ayUser.setId("3");
        ayUser.setName("t3");
        ayUser.setPassword("123");
        ayUserService.save(ayUser);
    }

    //测试redis
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("name","ay");
        String name=(String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
        redisTemplate.delete("name");
    }
}
