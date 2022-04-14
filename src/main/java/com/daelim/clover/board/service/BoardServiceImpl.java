package com.daelim.clover.board.service;


import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
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
    public void boardRemove(Integer boardId, Integer indexId) throws Exception {
        boardMapper.boardDelete(boardId,indexId);
    }

//    @Override
//    public List<Board> boardList() throws Exception {
//        return boardMapper.boardList();
//    }
    @Override // 일반검색 리스트
    public List<Board> boardList(Criteria cri) throws Exception {
        return boardMapper.getBoardListPaging(cri);
    }

    @Override //지도검색 리스트
    public List<Board> boardMapSearchList(Criteria cri) throws Exception {
        return boardMapper.getBoardMapSearchList(cri);
    }

    @Override //일반검색 총합
    public int listGetTotal(Criteria cri) throws Exception {
        return boardMapper.listGetTotal(cri);
    }

    @Override //지도검색 총합
    public int mapSearchlistGetTotal(Criteria cri) throws Exception {
        return boardMapper.mapSearchlistGetTotal(cri);
    }

    @Override //조회수
    public int boardCountSet(Integer boardId) throws Exception {
        return boardMapper.boardCountSet(boardId);
    }
}
