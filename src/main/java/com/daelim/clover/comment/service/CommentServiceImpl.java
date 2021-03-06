package com.daelim.clover.comment.service;

import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.comment.domain.Comment;
import com.daelim.clover.comment.domain.CommentDTO;
import com.daelim.clover.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{


    @Autowired
    private final CommentMapper mapper;

    @Override
    public int registerComment(Comment comment) throws Exception {
        return mapper.insertComment(comment);
    }

    @Override
    public Comment readComment(int commentId) throws Exception {
        return mapper.selectComment(commentId);
    }

    @Override
    public int modifyComment(Comment comment) throws Exception {
        return mapper.updateComment(comment);
    }

    @Override
    public int removeComment(int commentId) throws Exception {
        return mapper.deleteComment(commentId);
    }

    @Override
    public CommentDTO getListPage(Criteria cri, int boardId) throws Exception {
        return new CommentDTO(mapper.getCountByBoardId(boardId), mapper.commentPagingList(cri,boardId));
    }

    @Override
    public List<Comment> getPagingList(Criteria cri, int boardId) throws Exception {
        return mapper.commentPagingList(cri,boardId);
    }

    @Override
    public void userCommentAllDelete(String userId) throws Exception {
        mapper.userCommentAllDelete(userId);
    }
}

