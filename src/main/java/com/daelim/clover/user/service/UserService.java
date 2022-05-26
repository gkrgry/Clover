package com.daelim.clover.user.service;


import com.daelim.clover.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


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

    //유저 비번찾기
    public int searchUser(String email,String userId)throws Exception;
    //프로필 설정
    public String uploadFile(String userId,MultipartFile multipartFile) throws Exception;
    //카카오 유저 토큰
    public String getAccessToken(String code) ;
    //카카오 유저 정보가져오기
    public HashMap<String, Object> getUserInfo(String access_Token,User user);
}
