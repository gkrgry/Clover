package com.daelim.clover.likeList.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LikeList {

    private int likeId;//기본키
    private int boardId;//평점 넣을 게시판
    private String userId;//평점 넣는 사람
    private int grade;//평점
    private Date regDate;//최초 날짜
    private Date updateDate;//변경 날짜

}
