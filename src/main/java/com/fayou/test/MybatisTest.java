//package com.fayou.test;
//
//import com.fayou.dao.IUserDao;
//import com.fayou.domain.User;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//public class MybatisTest {
//    public static void main(String[] args) {
//        try {
//            InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
//            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
//            SqlSession session = factory.openSession();
//            IUserDao userDao = session.getMapper(IUserDao.class);
//            List<User> users = userDao.findAll();
//            for (User user : users) {
//
//            }
//            session.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
