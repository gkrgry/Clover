package com.daelim.clover.comment.service;

import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.comment.domain.Comment;

import java.util.List;

public interface CommentService {
    public int registerComment(Comment comment) throws Exception;

    public Comment readComment(int commentId) throws Exception;

    public int modifyComment(Comment comment) throws  Exception;

    public int removeComment(int commentId) throws Exception;


    public List<Comment> getPagingList(Criteria cri ,int boardId) throws Exception;
}
