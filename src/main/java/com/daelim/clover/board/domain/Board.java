package com.daelim.clover.board.domain;

import com.daelim.clover.user.domain.User;
import lombok.Data;


import java.util.Date;

@Data
public class Board {
    private int boardId; //기본키
    private int indexId; //유저 테이블 기본키
    private String title; //글 제목
    private String image; //글 이미지 파일 이름
    private String content; //글 내용
    private String genAddress; //도로명 주소
    private String detAddress; //상세 주소
    private Date regDate;       //생성날짜
    private Date updateDate;    //업데이트 날짜
    private String week;        //활동할 요일
    private String tagBigName;  //큰 태그
    private String tagSmallName; //작은 태그
    private int boardCount;    //글 조회수
    private double lat;          //위도
    private double lon;          // 경도

    private User user;          //유저 db

}
