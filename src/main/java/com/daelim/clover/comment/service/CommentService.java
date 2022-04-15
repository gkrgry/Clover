package com.daelim.clover.comment.service;

import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.comment.domain.Comment;

import java.util.List;

public interface CommentService {
    public int registerComment(Comment comment) throws Exception;

    public Comment readComment(int commentId) throws Exception;

    public int modifyComment(Comment comment) throws  Exception;

    public int removeComment(int commentId, int boardId, int indexId) throws Exception;

    public List<Comment> getList(Criteria cri, int comment_id) throws Exception;
}
