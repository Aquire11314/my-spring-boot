package com.example.demo.service;

import com.example.demo.model.AyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Collection;
import java.util.List;

public interface AyUserService {
    AyUser findById(String id);
    List<AyUser> findAll();
    AyUser save(AyUser ayUser);
    void delete(String id);

    //分页
    Page<AyUser> findAll(Pageable pageable);

    //自定义
    List<AyUser> findByName(String name);

    List<AyUser> findByNameLike(String name);

    List<AyUser> findByIdIn(Collection<String> ids);
}
