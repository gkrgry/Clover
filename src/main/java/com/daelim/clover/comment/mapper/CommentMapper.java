package com.daelim.clover.comment.mapper;

import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    public int insertComment(Comment comment) throws Exception;

    public Comment selectComment(int boardId) throws Exception;

    public int deleteComment(int boardId) throws Exception;

    public int updateComment(Comment comment) throws  Exception;

    public List<Comment> commentPagingList(@Param("cri") Criteria cri, @Param("boardId") int boardId) throws Exception;

    public int getCountByBoardId(int boardId) throws Exception;

    public int selectCommentTotal(Comment comment) throws Exception;
}
