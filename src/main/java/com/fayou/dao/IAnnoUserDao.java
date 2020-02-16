package com.fayou.dao;

import com.fayou.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @CacheNamespace(blocking = true)    开启二级缓存
 */
@CacheNamespace(blocking = true)
public interface IAnnoUserDao {

    @Select("select * from user")
    @Results(id = "userMap",value = {
            @Result(id=true,column = "id",property = "userId"),
            @Result(column = "username",property = "userName"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "birthday",property = "userBirthday"),
            @Result(column = "address",property = "userAddress"),
            //column为user表中的id字段
            @Result(property = "accounts",column = "id",many = @Many(select = "com.fayou.dao.IAnnoAccountDao.findAccountByUid",fetchType = FetchType.LAZY))

    })
    List<User> findAll();


    @Insert("insert into user(username,birthday,address,sex)values(#{userName},#{userBirthday},#{userAddress},#{userSex})")
    void saveUser(User user);

    @Update("update user set username=#{userName},birthday=#{userBirthday},address=#{userAddress},sex=#{userSex} where id = #{userId}")
    void updateUser(User user);


    @Delete("delete from user where id = #{uid}")
    void deleteUser(int uid);

    @Select("select * from user where id = #{userId}")
    @ResultMap(value = {"userMap"})
    User findById(Integer uid);
}
