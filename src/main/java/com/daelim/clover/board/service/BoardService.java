package com.daelim.clover.board.service;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {

    //게시글 등록
    public void boardRegister(Board board) throws Exception;
    //게시글 읽기
    public Board boardRead (Integer integer) throws  Exception;
    //게시글 수정
    public void boardModify(Board board) throws Exception;
    //게시글 삭제
    public void boardRemove(Integer boardId, String userId) throws Exception;

    //게시글 리스트
    public List<Board> boardList(Criteria cri) throws Exception;

    //주소로 검색 기능
    public List<Board> boardMapSearchList(Criteria cri) throws Exception;
    //게시글 총 개수
    public int listGetTotal(Criteria cri) throws Exception;
    //주소로 검색 총 개수
    public int mapSearchlistGetTotal(Criteria cri) throws Exception;

    //게시글 조회수 기능
    public int boardCountSet(Integer boardId) throws Exception;

    //마이페이지 리스트
    public List<Board> mypageListPaging(Integer skip,Integer amount,String userId) throws Exception;

    //마이페이지 리스트 총 개수
    public int mypageGetTotal(String userId) throws Exception;

    //메인 조회수 상위 리스트
    public List<Board> mainList() throws Exception;

    //신청 이메일 보내기
    public void sendSimpleMessage(String title, String to, String userId, String fromEmail,
                                  String nickname,String name)throws  Exception;

}
