package com.dsg.gyeonstagramapi.repository.querydsl;

import com.dsg.gyeonstagramapi.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllBy();
}
