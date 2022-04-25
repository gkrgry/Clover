package com.daelim.clover.comment.domain;

import com.daelim.clover.board.domain.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@Data
public class CommentDTO {

    private int commentCnt;
    private List<Comment> list;



}