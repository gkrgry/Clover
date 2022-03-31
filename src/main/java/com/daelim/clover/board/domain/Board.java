package com.daelim.clover.board.domain;

import lombok.Data;


import java.util.Date;

@Data
public class Board {
    private int boardId;
    private int indexId;
    private String title;
    private String image;
    private String content;
    private String genAddress;
    private String detAddress;
    private Date regDate;
    private Date updateDate;
    private String week;
    private String tagBigName;
    private String tagSmallName;
    private int board_count;

}
