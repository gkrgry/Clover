package com.daelim.clover.user.service;


import com.daelim.clover.user.domain.User;


public interface UserService {

    //회원가입
    public void userSingUp(User user) throws  Exception;

    //아이디 중복확인
    public int idCheck(String userId) throws Exception;
}
