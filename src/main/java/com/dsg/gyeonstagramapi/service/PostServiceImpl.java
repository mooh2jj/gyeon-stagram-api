package com.dsg.gyeonstagramapi.service;

import com.dsg.gyeonstagramapi.dto.PostDTO;
import com.dsg.gyeonstagramapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> list() {
        return postRepository.findAllBy().stream()
                .map(this::entityToDTO)
                .toList();
    }
}
