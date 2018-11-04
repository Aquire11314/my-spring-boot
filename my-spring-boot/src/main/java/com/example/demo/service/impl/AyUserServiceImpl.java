package com.example.demo.service.impl;

import com.example.demo.model.AyUser;
import com.example.demo.repository.AyUserRepository;
import com.example.demo.service.AyUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

//开启事务
@Transactional
@Service
public class AyUserServiceImpl implements AyUserService {
    @Resource(name="ayUserRepository")
    private AyUserRepository ayUserRepository;
    @Resource
    private RedisTemplate redisTemplate;
    private static final String ALL_USER="ALL_USER_LIST";

    @Override
    public AyUser findById(String id) {
        List<AyUser>ayUserList=redisTemplate.opsForList().range(ALL_USER,0,-1);
        if(ayUserList!=null&&ayUserList.size()>0){
            for(AyUser user:ayUserList){
                if(user.getId().equals(id)){
                    return user;
                }
            }
        }
        AyUser ayUser=ayUserRepository.findById(id).get();
        if(ayUser!=null){
            redisTemplate.opsForList().leftPush(ALL_USER,ayUser);
        }
        return ayUser;
    }

    @Override
    public List<AyUser> findAll() {
        return ayUserRepository.findAll();
    }

    //方法级别事务回覆盖类级别的事务
    @Transactional
    @Override
    public AyUser save(AyUser ayUser) {
        AyUser ayUser1=ayUserRepository.save(ayUser);



        if(ayUser!=null){
            redisTemplate.opsForList().leftPush(ALL_USER,ayUser);
        }
        return ayUser1;
    }

    @Override
    public void delete(String id) {
        AyUser ayUser=ayUserRepository.findById(id).get();
        ayUserRepository.deleteById(id);


        List<AyUser>ayUserList=redisTemplate.opsForList().range(ALL_USER,0,-1);
        if(ayUserList!=null&&ayUserList.size()>0){
            for(AyUser user:ayUserList){
                if(user.getId().equals(id)){
                    redisTemplate.opsForList().remove(ALL_USER,1,ayUser);
                }
            }
        }
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public List<AyUser> findByName(String name) {
        return ayUserRepository.findByName(name);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUser> findByIdIn(Collection<String> ids) {
        return ayUserRepository.findByIdIn(ids);
    }
}
