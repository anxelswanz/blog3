package com.ansel.service;

import com.ansel.pojo.User;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description userservice
 */
public interface UserService {
    User checkUserByUsername(String username);
    User checkUser(String username, String password);
    void saveUser(User user);
}
