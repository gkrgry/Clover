package com.daelim.clover.user.service;


import com.daelim.clover.user.domain.User;


public interface UserService {



    //회원가입
    public void userSingUp(User user) throws  Exception;

    //아이디 중복확인
    public int idCheck(String userId) throws Exception;

    //이메일 인증
    String sendSimpleMessage(String to)throws  Exception;

    //이메일 중복확인
    public int emailCheck(String email)throws Exception;

    //내정보 불러오기
    public User myPage(String userId)throws Exception;

    //내정보 변경
    public int userUpdate(User user) throws Exception;

    //유저 삭제
    public void userDrop(String userId) throws Exception;

    //유저 아이디 찾기
    public String searchUser(String email)throws Exception;

}
