package com.daelim.clover.board.service;


import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper boardMapper;

    @Override
    public void boardRegister(Board board) throws Exception {
        boardMapper.boardCreate(board);
    }

    @Override
    public Board boardRead(Integer boardId) throws Exception {
        return boardMapper.boardRead(boardId);
    }

    @Override
    public void boardModify(Board board) throws Exception {
        boardMapper.boardUpdate(board);
    }

    @Override
    public void boardRemove(Board board) throws Exception {
        boardMapper.boardDelete(board);
    }

    @Override
    public List<Board> boardList() throws Exception {
        return boardMapper.boardList();
    }
}
