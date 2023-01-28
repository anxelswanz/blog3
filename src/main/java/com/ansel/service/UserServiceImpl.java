package com.ansel.service;



import com.ansel.dao.UserDao;
import com.ansel.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description
 */
@Service
public class UserServiceImpl implements UserService{


    @Autowired(required = false)
    private UserDao userRepository;


    @Override
    public User checkUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }



    @Test
    @Override
    public void saveUser(User user) {
        System.out.println("执行 saveandflush");
        userRepository.saveAndFlush(user);
    }
}
