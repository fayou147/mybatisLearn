package com.fayou.dao;

import com.fayou.domain.QueryVo;
import com.fayou.domain.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll();

    void saveUser(User user);

    void deleteUser(int id);


    User findById(int id);

    List<User> findByName(String username);


    List<User> findByQueryVo(QueryVo vo);

    List<User> findUserByCondition(User user);

    List<User> findUserInIds(QueryVo queryVo);
}
