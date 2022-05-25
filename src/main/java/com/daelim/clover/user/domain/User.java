package com.daelim.clover.user.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.Collections;
import java.util.Date;

@Log4j2
@Data
public class User implements UserDetails {

    private int indexId;        //유저인데스 넘버
    private String userId;      //유저아이디
    private String pwd;         //비번
     String name;        //이름
    private String nickname;    //별명
    private String email;       //이메일
    private String phone;       //연락처
    private int sex;            //성별
    Date regdate;       //가입날짜
    private String image; //이미지 Url
    int kakao;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("진입");
            return Collections.singletonList(new SimpleGrantedAuthority("USER"));
//        return Collections.singletonList(new SimpleGrantedAuthority(this.userId));
     //   return null;

    }

    @Builder
    public User(String userId,String pwd){
            log.info("빌드");
            log.info(indexId);
            log.info(userId);
            this.userId=userId;
            this.pwd=pwd;
    }
    @Override
    public String getPassword() {
        log.info(pwd+"패스워드 호출");

        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
