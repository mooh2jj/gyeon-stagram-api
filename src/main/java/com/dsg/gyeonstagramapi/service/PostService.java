package com.dsg.gyeonstagramapi.service;

import com.dsg.gyeonstagramapi.dto.PostDTO;
import com.dsg.gyeonstagramapi.entity.Post;

import java.util.List;

public interface PostService {
    List<PostDTO> list();

    default PostDTO entityToDTO(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .imageUrl(post.getImageUrl() == null ? null : post.getImageUrl())
                .writer(post.getMember().getName())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
