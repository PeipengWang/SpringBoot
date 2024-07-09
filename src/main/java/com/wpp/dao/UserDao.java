package com.wpp.dao;

import com.wpp.pojo.User;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface UserDao {
    public User getUserById1(int id);
    public User getUserById2(int id);
}
