package com.daelim.clover.comment.domain;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.user.domain.User;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int commentId;
    private int boardId;
    private int indexId;
    private String content;
    private Date regDate;
    private Date updateDate;
    
    private User user; //join 할 테이블 user_db 의 도메인
    private Board board;//join 할 테이블 board_db 의 도메인
}
