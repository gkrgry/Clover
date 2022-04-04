package com.daelim.clover.user.mapper;

import com.daelim.clover.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void saveUser(User user); //회원가입
}
