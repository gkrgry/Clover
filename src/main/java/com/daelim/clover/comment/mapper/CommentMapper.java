package com.daelim.clover.comment.mapper;

import com.daelim.clover.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public int insertComment(Comment comment) throws Exception;

    public Comment selectComment(int commentId) throws Exception;

    public int updateComment(Comment comment) throws  Exception;

    public int deleteComment(int commentId) throws Exception;

    public List<Comment> selectCommentList(Integer boardId) throws Exception;

    public int selectCommentTotal(Comment comment) throws Exception;
}
