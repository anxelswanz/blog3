package com.ansel.dao;

import com.ansel.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface UserDao extends JpaRepository<User,Long> {
  User findByUsernameAndPassword(String username, String password);
  User findByUsername(String username);
  @Override
  <S extends User> S saveAndFlush(S entity);
}
