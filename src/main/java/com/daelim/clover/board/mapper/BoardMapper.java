package com.daelim.clover.board.mapper;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public void boardCreate(Board board) throws Exception;

    public Board boardRead(Integer boardId) throws Exception;

    public void boardUpdate(Board board) throws Exception;

    public void boardDelete(Integer boardId, String userId) throws Exception;

    public void userAllDelete(String userId) throws Exception;

    public List<Board> boardList() throws Exception;

    public List<Board> getBoardListPaging(Criteria cri) throws Exception;

    public List<Board> getBoardMapSearchList(Criteria cri) throws Exception;

    public int listGetTotal(Criteria cri) throws Exception;

    public int mapSearchlistGetTotal(Criteria cri) throws Exception;

    public int boardCountSet(Integer boardId) throws Exception;


    public List<Board> mypageListPaging(Integer skip,Integer amount,String userId) throws Exception;

    public int mypageGetTotal(String userId) throws Exception;

    public List<Board> mainList() throws Exception;



}
