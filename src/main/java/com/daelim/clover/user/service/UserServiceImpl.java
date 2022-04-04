package com.daelim.clover.user.service;


import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void  userSingUp(User user) throws Exception{
        userMapper.saveUser(user);
    }

}
