package com.fayou.dao;

import com.fayou.domain.Account;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAnnoAccountDao {
    @Select("select * from account")
    @Results(id = "accountMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "money",property = "money"),
            @Result(property = "user",column = "uid",one = @One(select = "com.fayou.dao.IAnnoUserDao.findById",fetchType = FetchType.LAZY))
    })
    /**
     * column = "money",property = "money"   把column的值赋值给property；
     * 懒加载：把column的值当做参数赋值给select，查询出来的结果赋值给property
     */
    List<Account> findAll();



    @Select("select * from account where uid = #{userId}")
    List<Account> findAccountByUid(Integer userId);

}
