package com.daelim.clover.user.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int indexId;        //유저인데스 넘버
    private String userId;      //유저아이디
    private String pwd;         //비번
    private String name;        //이름
    private String nickname;    //별명
    private String email;       //이메일
    private String phone;       //연락처
    private int sex;            //성별
    private Date regdate;       //가입날짜

}
