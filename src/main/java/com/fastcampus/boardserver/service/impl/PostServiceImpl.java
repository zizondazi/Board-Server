package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;;

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if(memberInfo.getId() != 0){
            postMapper.register(postDTO);
        }else {
            log.error("register ERROR {}", postDTO.toString() );
            throw new RuntimeException("register ERROR");
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(accountId);
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if(postDTO != null && postDTO.getId() != 0){
            postMapper.updatePosts(postDTO);
        }else {
            log.error("updatePosts ERROR {}", postDTO.toString() );
            throw new RuntimeException("updatePosts ERROR");
        }
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId != 0 && postId != 0){
            postMapper.deletePosts(userId, postId);
        }else {
            log.error("deletePosts ERROR {}", postId );
            throw new RuntimeException("deletePosts ERROR");
        }
    }
}
