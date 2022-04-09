package com.daelim.clover.user.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Log4j2
@Data
public class User implements UserDetails {

    private int indexId;        //유저인데스 넘버
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;      //유저아이디

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$.{8,16})",message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pwd;         //비번
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z]{2,10}$",message = "이름은 특수문자를 제외한 2~10자리어야합니다. ")
    private String name;        //이름
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$",message = "닉네임은 특수문자를 제외한 2~10자리어야합니다. ")
    private String nickname;    //별명
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$",message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일 필수 입력 값입니다.")
    private String email;       //이메일
    @Pattern(regexp = "[0-9]{11}$")
    @NotBlank(message = "연락처는 필수입력사항입니다.")
    private String phone;       //연락처
    private int sex;            //성별
        Date regdate;       //가입날짜



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("진입");
       return null;
               //Collections.singletonList(new SimpleGrantedAuthority(this.userId));

    }
    @Builder
    public User(String userId,String pwd){
            this.userId=userId;
            this.pwd=pwd;


    }
    @Override
    public String getPassword() {
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
