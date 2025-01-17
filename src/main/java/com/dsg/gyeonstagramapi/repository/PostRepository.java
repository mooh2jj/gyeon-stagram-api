package com.dsg.gyeonstagramapi.repository;

import com.dsg.gyeonstagramapi.entity.Post;
import com.dsg.gyeonstagramapi.repository.querydsl.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
