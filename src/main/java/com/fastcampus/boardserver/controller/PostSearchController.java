package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postDTO) {
        List<PostDTO> posts = postSearchService.getPosts(postDTO);

        return new PostSearchResponse(posts);
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        List<PostDTO> posts = postSearchService.getPostsByTagName(tagName);

        return new PostSearchResponse(posts);
    }

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> posts;
    }
}
