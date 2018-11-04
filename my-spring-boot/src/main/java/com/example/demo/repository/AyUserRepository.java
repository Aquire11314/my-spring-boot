package com.example.demo.repository;

import com.example.demo.model.AyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

//JpaRepository是不开启事务的，SimpleJpaRepository才默认开启，而事务一般在服务层开启，所以在AyUserServiceImpl使用的注解事务
public interface AyUserRepository extends JpaRepository<AyUser,String> {

    List<AyUser> findByName(String name);

    List<AyUser> findByNameLike(String name);

    List<AyUser> findByIdIn(Collection<String> ids);
}
