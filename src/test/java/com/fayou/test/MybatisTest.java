package com.fayou.test;

import com.fayou.dao.IAccountDao;
import com.fayou.dao.IUserDao;
import com.fayou.domain.Account;
import com.fayou.domain.QueryVo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MybatisTest {

    //OGNL

    private SqlSession session;
    private IUserDao userDao;
    private IAccountDao accountDao;
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
        userDao = session.getMapper(IUserDao.class);
        accountDao = session.getMapper(IAccountDao.class);
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
    public void testSave() {
        User user = new User();
        user.setUserName("fayou002");
        user.setUserBirthday(new Date(System.currentTimeMillis()));
        user.setUserSex("男");
        user.setUserAddress("青岛市城阳区");

        userDao.saveUser(user);

        System.out.println(user.toString());
    }
    @Test
    public void testDelete(){
        userDao.deleteUser(3);
    }

    @Test
    public void testFindByName(){
        List<User> users = userDao.findByName("i");
        users.forEach(user -> System.out.println(user.toString()));
    }


    @Test
    public void testFindByQueryVo(){
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUserName("i%");
        vo.setUser(user);
        List<User> users = userDao.findByQueryVo(vo);
        users.forEach(u -> System.out.println(u.toString()));
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        users.forEach(u -> System.out.println(u.toString()));
    }


    @Test
    public void testFindUserByCondition(){
        User user  = new User();
        user.setUserName("ijn");
        user.setUserSex("女");
        List<User> users = userDao.findUserByCondition(user);
        users.forEach(u -> System.out.println(u.toString()));
    }


    @Test
    public void testFindUserInIds(){
        QueryVo vo = new QueryVo();
        List<Integer> ids = List.of(5,6,7);

        vo.setIds(ids);
        List<User> users = userDao.findUserInIds(vo);
        users.forEach(u -> System.out.println(u.toString()));
    }

    @Test
    public void testFindAccountAll(){
        List<Account> accounts = accountDao.findAll();
        accounts.forEach(account -> System.out.println(account.toString()));
    }


    /**
     * 一级缓存是sqlSession范围的缓存，当调用sqlSession的修改，添加，删除，commit()，close()
     * 等方法时，就会清空以及缓存
     */
    @Test
    public void testFirstLevelCache(){
        User user1 = userDao.findById(7);

        System.out.println(user1);

        session.clearCache();
        User user2 = userDao.findById(7);

        System.out.println(user2);
        System.out.println(user1==user2);

    }


    /**
     * 二级缓存缓存的是数据，而不是对象本身
     */
    @Test
    public void testSecondLevelCache(){
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session1 = factory.openSession();
            IUserDao userDao1 = session1.getMapper(IUserDao.class);
            User user1 = userDao1.findById(7);

            System.out.println(user1);
            session1.close();

            SqlSession session2 = factory.openSession();
            IUserDao userDao2 = session2.getMapper(IUserDao.class);
            User user2 = userDao2.findById(7);
            session2.close();
            System.out.println(user2);

            System.out.println(user1==user2);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
