package com.fayou.test;

import com.fayou.dao.IAccountDao;
import com.fayou.dao.IAnnoAccountDao;
import com.fayou.dao.IAnnoUserDao;
import com.fayou.dao.IUserDao;
import com.fayou.domain.Account;
import com.fayou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.function.Consumer;

public class MybatisAnnoTest {

    private SqlSession session;
    private IAnnoUserDao userDao;
    private IAnnoAccountDao accountDao;
    InputStream inputStream = null;

    @Before
    public void init() {

        try {
            inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        session = factory.openSession();
        userDao = session.getMapper(IAnnoUserDao.class);
        accountDao = session.getMapper(IAnnoAccountDao.class);

    }


    @After
    public void destroy(){
        session.commit();
        session.close();

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user);
            }
        });
    }

    @Test
    public void  testSave(){
        User user = new User();
        user.setUserName("fayouTest");
        user.setUserBirthday(new Date(System.currentTimeMillis()));
        user.setUserSex("男");
        user.setUserAddress("青岛市城阳区");

        userDao.saveUser(user);

        System.out.println(user.toString());
    }

    @Test
    public void  testUpdate(){
        User user = new User();
        user.setUserId(11);
        user.setUserName("fayou004");
        user.setUserBirthday(new Date(System.currentTimeMillis()));
        user.setUserSex("男");
        user.setUserAddress("青岛市胶州市");

        userDao.updateUser(user);

        System.out.println(user.toString());
    }

    @Test
    public void  testDelete(){

        userDao.deleteUser(11);

    }

    @Test
    public void findAccountAll(){
        List<Account> accounts = accountDao.findAll();
        accounts.forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {

                System.out.println(account);
                System.out.println(account.getUser());
            }
        });
    }
}
