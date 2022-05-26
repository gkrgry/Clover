package com.daelim.clover.user.kakao;

import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.mapper.UserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserRepository {
    @Autowired
    UserMapper userMapper;

    User user;
    //정보 저장
    public void kakaoinsert(HashMap<String,Object> userInfo,User user){
//        sql.insert("com.daelim.clover.user.mapper.UserMapper.akakoInsert",userInfo);
        System.out.println(userInfo.get("email").toString());
        System.out.println(userInfo.get("nickname").toString());
        System.out.println(userInfo.get("gender").toString());
       String email=userInfo.get("email").toString();
        System.out.println(email);
        System.out.println(user.getUserId());

        user.setUserId(userInfo.get("email").toString());
        user.setName(userInfo.get("nickname").toString());
        user.setEmail(userInfo.get("email").toString());
        user.setNickname(userInfo.get("nickname").toString());

        if(userInfo.get("gender").toString().equals("male")){
            user.setSex(1);
        }else {
            user.setSex(0);
        }
        user.setKakao(1);

        System.out.println(user);
        userMapper.saveUser(user);
        System.out.println(userInfo+"정보저장");
    }

    //정보 확인
    public KakaDTO findkakao(HashMap<String,Object> userInfo,User user){
        System.out.println("RN :"+ userInfo.get("nickname"));
        System.out.println("RE :" + userInfo.get("email"));
        System.out.println("RG :" +userInfo.get("gender"));
        user=userMapper.getUserAccount(userInfo.get("email").toString());
        System.out.println(user);
        KakaDTO kd = new KakaDTO();
        if(user!=null){
            kd.setK_email(user.getEmail());
            kd.setK_gender(userInfo.get("gender").toString());
            kd.setK_name(user.getName());
            System.out.println(kd);
        }

        return kd;
    }

}
