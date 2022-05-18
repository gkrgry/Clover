package com.daelim.clover.user.mapper;

import com.daelim.clover.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

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

    //정보수정
    public int UpdateUser(User user);

    //유저 삭제
    public void UserDrop(String userId);

    //유저 아이디 찾기
    public String SearchUser(String email);

    //유저 비번찾기
    public int SearchPwd(String email,String userId);

    //유저 이미지 설정
    public int ImageUpdate(String userId, String image);


}
