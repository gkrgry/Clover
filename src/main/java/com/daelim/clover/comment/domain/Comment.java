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
}
