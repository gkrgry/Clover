package com.daelim.clover.board.controller;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.board.domain.FileDTO;
import com.daelim.clover.board.domain.PageDTO;
import com.daelim.clover.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BoardController {


    private final BoardService boardService;

//    @GetMapping("/list")
//    public String boardList(Model model) throws Exception{
//        log.info("List 입니다");
//
//        List<Board> boardList = boardService.boardList();
//
//        model.addAttribute("boardList", boardList);
//
//        return "bList";
//    }
    @GetMapping("/main")
    public String main(Model model) throws Exception{


    return "mainpage";
}
    @GetMapping("/mapSearch")
    public String mapSearch(Criteria cri, Model model) throws Exception{
        log.info("map search");

        //boardlist - > boardMaplist
        List<Board> boardMapSearchList = boardService.boardMapSearchList(cri);
        int total = boardService.mapSearchlistGetTotal(cri);
        total = total + 10;
        log.info(total);
        model.addAttribute("total",total);
        model.addAttribute("boardList", boardMapSearchList);
        model.addAttribute("pageMaker",new PageDTO(cri,total));

        return "bMapSearch";
    }
    @GetMapping("/list")
    public String boardList(Criteria cri,Model model) throws Exception{
        log.info("cri +List 입니다");

        List<Board> boardList = boardService.boardList(cri);
        int total = boardService.listGetTotal(cri);
        total = total + 10;
        log.info(total);
        model.addAttribute("total",total);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageMaker",new PageDTO(cri,total));
        return "bList";
    }



    @GetMapping("/register")
    public String boardRegisterForm(Board board, Model model) throws Exception{
        log.info("get 입력 입니다.");


        return "bRegister";
    }

    @PostMapping("/register")
    public String boardRegister(Board board, Model model , @RequestParam MultipartFile[] uploadfile) throws Exception{

        //유효성 검사
        if(board.getUserId().equals("") || board.getUserId() == null ){
            log.info("null");
            return "redirect:/login";
        }else{
            //파일 업로드
            for(MultipartFile multipartFile : uploadfile){
                //UUID를 이용하요 이름이 겹치지 않게 랜덤하게 이름 저장
                FileDTO dto = new FileDTO(UUID.randomUUID().toString(),
                        multipartFile.getOriginalFilename(),
                        multipartFile.getContentType());
                String fileName = dto.getFileId()+"_"+dto.getFileName();
                File file = new File(fileName);
                multipartFile.transferTo(file);
                board.setImage(fileName);
            }
            log.info("post 입력 입니다.");
            boardService.boardRegister(board);
            return "redirect:/list";
        }

    }
    @GetMapping("/read")
    public String boardRead(@RequestParam("boardId") int boardId, Model model, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) throws Exception{
        Cookie oldCookie = null;//비교하기 위한 쿠기
        Cookie[] cookies = request.getCookies();
        log.info("read 입력 입니다.");


        Board board = boardService.boardRead(boardId);

        //쿠키가 있을경우
        if(cookies != null && cookies.length > 0){
            for(int i=0; i< cookies.length; i++){
                // cookie의 name이 cookie + boardId와 일치하는 쿠키를 oldCookie에 넣어줌
                if(cookies[i].getName().equals("cookie"+boardId)){
                    log.info("쿠키 처음 생성한 뒤 돌아옴");
                    oldCookie = cookies[i];
                }
            }
        }


        if(oldCookie == null) {
            //쿠키 생성
            Cookie newCookie = new Cookie("cookie" + boardId, "|" + boardId + "|");
            //쿠키 추가
            response.addCookie(newCookie);

            //쿠키를 추가 시키고 조회수를 증가
            int boardCountSet = boardService.boardCountSet(boardId);//조회수 증가
            model.addAttribute("boardCountSet", boardCountSet);

        }else { //oldCookie 가 null 아닌거 즉 쿠기가 있으므로 조회수 증가 로직을 처리 X
            String value = oldCookie.getValue();
            log.info(value);
        }



        model.addAttribute("board",board);


        return "bRead";
    }

    @GetMapping("/modify")
    public String boardModify(@RequestParam("boardId") int boardId, Model model) throws Exception{


        Board board = boardService.boardRead(boardId);

        model.addAttribute("board",board);


        return "bModify";
    }

    @PostMapping("/modify")
    public String boardModifyForm(Board board, Model model,HttpSession session) throws Exception{
        //유효성 검사
        if(board.getUserId() == session.getAttribute("sUserId")){

            boardService.boardModify(board);
            return "redirect:/read";
        }else{

            return "redirect:/login";
        }




    }



    @PostMapping("/remove")
    public String boardRemove(@RequestParam("boardId") int boardId
                              ,@RequestParam("userId") String userId
                              ,Model model) throws Exception{
        log.info("remove.....");

        boardService.boardRemove(boardId,userId);

        return "redirect:/main";
    }



}
