package com.fastcampus.boardserver.service.impl;


import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import com.fastcampus.boardserver.utils.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public void register(UserDTO userProfile) {
        boolean isDuplicateId = isDuplicatedId(userProfile.getUserId());
        if(isDuplicateId) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userProfile.setCreateTime(new Date());
        userProfile.setPassword(SHA256Util.encryptSHA256(userProfile.getPassword()));

    }

    @Override
    public UserDTO login(String id, String password) {
        return null;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return false;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return null;
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {

    }

    @Override
    public void deleteId(String id, String password) {

    }
}
