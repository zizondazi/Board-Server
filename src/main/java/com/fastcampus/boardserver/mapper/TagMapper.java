package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    public int registerTag(TagDTO comment);
    public void updateTag(TagDTO comment);
    public void deleteTag(int tagId);
    public void createPostTag(Integer tagId, Integer postId);
}
