package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper postSearchMapper;

    @Cacheable(value ="getPosts", key ="'getPosts' + #postSearchRequest.getContents() + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> posts = null;
        try {
            posts = postSearchMapper.selectPosts(postSearchRequest);
        }catch (RuntimeException e) {
            log.error("getPosts ERROR " + e.getMessage());
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<PostDTO> getPostsByTagName(String tagName) {
        List<PostDTO> posts = null;
        try {
            posts = postSearchMapper.selectPostsByTagName(tagName);
        }catch (RuntimeException e) {
            log.error("getPostsByTagName ERROR " + e.getMessage());
            e.printStackTrace();
        }
        return posts;
    }
}
