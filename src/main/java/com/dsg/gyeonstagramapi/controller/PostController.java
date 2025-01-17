package com.dsg.gyeonstagramapi.controller;

import com.dsg.gyeonstagramapi.dto.PostDTO;
import com.dsg.gyeonstagramapi.service.PostService;
import com.dsg.gyeonstagramapi.util.file.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CustomFileUtil fileUtil;

    // 리스트조회
    @GetMapping("/list")
    public ResponseEntity<List<PostDTO>> list() {
        log.info("post list");
        List<PostDTO> dtoList = postService.list();
        return ResponseEntity.ok(dtoList);
    }

    // 이미지 불러오기
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);

    }

}
