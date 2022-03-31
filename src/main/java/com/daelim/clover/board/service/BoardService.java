package com.daelim.clover.board.service;

import com.daelim.clover.board.domain.Board;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {

    public void boardRegister(Board board) throws Exception;

    public Board boardRead (Integer integer) throws  Exception;

    public void boardModify(Board board) throws Exception;

    public void boardRemove(Board board) throws Exception;

    public List<Board> boardList() throws Exception;
}
