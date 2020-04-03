package com.community.service;

import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdateUser(User user) {
        User u = userMapper.getUserByAccountId(Long.valueOf(user.getAccountId()));
        if (u == null) {
            userMapper.insert(user);
        } else {
            userMapper.updateUser(user, u.getId());
        }
    }
}
