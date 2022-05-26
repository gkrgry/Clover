package com.daelim.clover.board.service;


import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.board.mapper.BoardMapper;
import com.daelim.clover.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    JavaMailSender emailSender;


    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;
    @Value("${profileImg.path}")
    private String uploadFolder;

    private MimeMessage createMessage(String title, String to, String userId, String fromEmail,
                                      String nickname,String name)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("보낸 대상 : "+ userId);

        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);//보내는 대상
        message.setSubject("Clover 동호회 신청 메일입니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Clover 입니다.. </h1>";
        msgg+= "<h3>" + userId + "("+ nickname + ") 님의 " + title + " 신청 메일 입니다. <h3>";
        msgg+= "<br>";
        msgg+= "<h3>닉네임 : "+ nickname +"<h5>";
        msgg+= "<h3>이름 : "+ name +"<h5>";
//        msgg+= "<h5>전화번호 : "+ user.getPhone() +"<h5>";
        msgg+= "<h3>이메일 : "+ fromEmail +"<h5>";
        msgg+= "<br>";
        msgg+= "<h3>감사합니다!</h3>";
        msgg+= "<br>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress(id,"Clover"));//보내는 사람

        return message;
    }

    @Override
    public void sendSimpleMessage(String title, String to, String userId, String fromEmail
            ,String nickname,String name) throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(title,to,userId,fromEmail,nickname,name);
        System.out.println(message);
        try{//예외처리
            emailSender.send(message);
        }catch(Exception es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

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
    public void boardRemove(Integer boardId, String userId) throws Exception {
        boardMapper.boardDelete(boardId,userId);
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


    @Override
    public List<Board> mypageListPaging(Integer skip,Integer amount,String userId) throws Exception {
        return boardMapper.mypageListPaging(skip,amount,userId);
    }

    @Override
    public int mypageGetTotal(String userId) throws Exception {
        return boardMapper.mypageGetTotal(userId);
    }

    @Override
    public List<Board> mainList() throws Exception {
        return boardMapper.mainList();
    }

    @Override
    public void userAllDelete(String userId) throws Exception {
        boardMapper.userAllDelete(userId);
    }
}
