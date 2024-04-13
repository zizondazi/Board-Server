package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    public UserDTO getUserProfile(@Param("userId") String userId);

    int insertUserProfile(UserDTO userDTO);

    int deleteUserProfile(@Param("userId") String userId);

    public UserDTO indByIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    int isDuplicatedId(@Param("userId") String userId);

    public int updatePasswrod(UserDTO userDTO);
}
