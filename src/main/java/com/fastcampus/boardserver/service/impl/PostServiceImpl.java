package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.CommentMapper;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.TagMapper;
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

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if(memberInfo.getId() != 0){
            postMapper.register(postDTO);
            Integer postId = postDTO.getId();
            for(int i=0; i<postDTO.getTags().size(); i++) {
                TagDTO tagDTO = postDTO.getTags().get(i);
                tagMapper.registerTag(tagDTO);
                Integer tagId = tagDTO.getId();
                tagMapper.createPostTag(tagId, postId);
            }
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

    @Override
    public void registerPostComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() != 0) {
            commentMapper.registerComment(commentDTO);
        }else {
            log.error("registerPostComment ERROR {}", commentDTO.toString() );
            throw new RuntimeException("registerPostComment ERROR");
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO != null) {
            commentMapper.updateComment(commentDTO);
        }else {
            log.error("updateComment ERROR");
            throw new RuntimeException("updateComment ERROR");
        }
    }

    @Override
    public void deleteComment(int userId, int commentId) {
        if(userId != 0 && commentId != 0) {
            commentMapper.deleteComment(commentId);
        }else {
            log.error("deleteComment ERROR"+ commentId);
            throw new RuntimeException("deleteComment ERROR" + commentId);
        }
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.registerTag(tagDTO);
        }else {
            log.error("registerTag ERROR");
            throw new RuntimeException("registerTag ERROR");
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.updateTag(tagDTO);
        }else {
            log.error("updateTag ERROR");
            throw new RuntimeException("updateTag ERROR");
        }
    }

    @Override
    public void deleteTag(int userId, int tagId) {
        if(userId != 0 && tagId != 0) {
            tagMapper.deleteTag(tagId);
        }else {
            log.error("deleteTag ERROR" + tagId);
            throw new RuntimeException("deleteTag ERROR" + tagId);
        }
    }
}
