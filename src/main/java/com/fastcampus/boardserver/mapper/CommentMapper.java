package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public int registerComment(CommentDTO comment);
    public void updateComment(CommentDTO comment);
    public void deleteComment(int commentId);
}
