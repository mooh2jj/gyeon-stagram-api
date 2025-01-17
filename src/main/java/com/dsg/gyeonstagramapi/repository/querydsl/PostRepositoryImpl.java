package com.dsg.gyeonstagramapi.repository.querydsl;

import com.dsg.gyeonstagramapi.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dsg.gyeonstagramapi.entity.QMember.member;
import static com.dsg.gyeonstagramapi.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findAllBy() {

        return queryFactory
                .select(post)
                .from(post)
                .leftJoin(post.member, member).fetchJoin()
                .fetch();
    }
}
