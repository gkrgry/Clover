package com.daelim.clover.board.service;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {

    public void boardRegister(Board board) throws Exception;

    public Board boardRead (Integer integer) throws  Exception;

    public void boardModify(Board board) throws Exception;

    public void boardRemove(Integer boardId, Integer indexId) throws Exception;

//    public List<Board> boardList() throws Exception;
    public List<Board> boardList(Criteria cri) throws Exception;

    //주소로 검색 기능
    public List<Board> boardMapSearchList(Criteria cri) throws Exception;

    public int listGetTotal(Criteria cri) throws Exception;

    public int mapSearchlistGetTotal(Criteria cri) throws Exception;

}
