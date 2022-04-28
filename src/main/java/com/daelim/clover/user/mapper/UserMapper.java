package com.daelim.clover.user.mapper;

import com.daelim.clover.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void saveUser(User user); //회원가입

    //로그인 시큐리티
    User  getUserAccount(String userId);

    //아이디 중복 검사
    public int idCheck(String userId);

    //이메일 중복 검사
    public int emailCheck(String email);

    //내정보 가져오기
    public User selectionUser(String userId);
}
