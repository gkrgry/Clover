package com.daelim.clover.user.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
public class User implements UserDetails {

        int indexId;        //유저인데스 넘버
        String userId;      //유저아이디
        String pwd;         //비번
        String name;        //이름
        String nickname;    //별명
        String email;       //이메일
        String phone;       //연락처
        int sex;            //성별
        Date regdate;       //가입날짜

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.singletonList(new SimpleGrantedAuthority(this.userId));

    }
    @Builder
    public User(int indexId ,String userId,String password){
        this.indexId=indexId;
        this.userId=userId;
        this.pwd=password;
    }
    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    public String getUserName(){
        return this.name;
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
