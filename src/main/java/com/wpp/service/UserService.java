package com.wpp.service;

import com.wpp.dao.UserDao;
import com.wpp.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void testTransactionUser(int id){
        User user2 = userDao.getUserById1(id);
        User user1 = userDao.getUserById1(id);
        System.out.println(user1);
        System.out.println(user1 == user2);
    }
    public void testTransactionUser2(int id){
        User user2 = userDao.getUserById1(id);
        User user1 = userDao.getUserById1(id);
        System.out.println(user1);
        System.out.println(user1 == user2);
    }
}
