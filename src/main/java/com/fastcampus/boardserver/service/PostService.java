package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int userId, int postId);

    void registerPostComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(int userId, int commentId);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deleteTag(int userId, int tagId);
}
