package com.daelim.clover.board.controller;

import com.daelim.clover.board.domain.*;
import com.daelim.clover.board.service.BoardService;
import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BoardController {


    private final BoardService boardService;

    private final UserService userService;

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
    @GetMapping({"/main", "/"})
    public String main(Model model) throws Exception {

        List<Board> mainList = boardService.mainList();
        model.addAttribute("boardList", mainList);
        return "mainpage";
    }

    @GetMapping("/mapSearch")
    public String mapSearch(Criteria cri, Model model) throws Exception {
        log.info("map search");
        List<Board> boardMapSearchList = boardService.boardMapSearchList(cri);
        int total = boardService.mapSearchlistGetTotal(cri);
        total = total + 10;
        log.info(total);
        model.addAttribute("total", total);
        model.addAttribute("boardList", boardMapSearchList);
        model.addAttribute("pageMaker", new PageDTO(cri, total));

        return "bMapSearch";
    }

    @GetMapping("/list")
    public String boardList(Criteria cri, Model model) throws Exception {
        log.info("cri +List 입니다");

        List<Board> boardList = boardService.boardList(cri);
        int total = boardService.listGetTotal(cri);
        total = total + 10;
        log.info(total);
        model.addAttribute("total", total);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "bList";
    }

    @GetMapping("/mypageList")
    public String mypageList(Criteria cri, Model model, HttpServletRequest request) throws Exception {
        log.info("cri +List 입니다");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("sUserId");
        List<Board> boardList = boardService.mypageListPaging(cri.getSkip(), cri.getAmount(), userId);
        int total = boardService.mypageGetTotal(userId);
        total = total + 10;
        model.addAttribute("total", total);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "include/bMypageList";
    }

//    //마이페이지 리스트 ajax
//    @ResponseBody
//    @GetMapping(value = "/mypage/pages/{userId}/{pageNum}",
//            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
//    public List<Board> mypageList(@PathVariable("pageNum") int pageNum, @PathVariable("userId") String userId,Criteria  cri) throws Exception {
//        log.info("getList.........");
//        List<Board> board = boardService.mypageListPaging(cri.getSkip(),cri.getAmount(),userId);
//        log.info("cri " + cri);
//        return board;
//    }

    @GetMapping("/register")
    public String boardRegisterForm(Board board, Model model) throws Exception {
        log.info("get 입력 입니다.");

        return "bRegister";
    }

    @PostMapping("/register")
    public String boardRegister(Board board, Model model, @RequestParam MultipartFile[] uploadfile) throws Exception {
        //유효성 검사
        if (board.getUserId().equals("") || board.getUserId() == null) {
            log.info("null");
            return "redirect:/login";
        } else {

            //파일 업로드
            for (MultipartFile multipartFile : uploadfile) {
                //UUID를 이용하요 이름이 겹치지 않게 랜덤하게 이름 저장
                FileDTO dto = new FileDTO(UUID.randomUUID().toString(),
                        multipartFile.getOriginalFilename(),
                        multipartFile.getContentType());
                String fileName = dto.getFileId() + "_" + dto.getFileName();
                File file = new File(fileName);
                log.info(file.getPath());
                multipartFile.transferTo(file);

                if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().toString().equals("")) {
                    board.setImage("NotImg.png");
                } else {
                    board.setImage(fileName);
                }
            }
            log.info("post 입력 입니다.");
            boardService.boardRegister(board);
            return "redirect:/list";
        }

    }

    @GetMapping("/read")
    public String boardRead(@RequestParam("boardId") int boardId, Model model, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) throws Exception {
        Cookie oldCookie = null;//비교하기 위한 쿠기
        Cookie[] cookies = request.getCookies();
        log.info("read 입력 입니다.");


        Board board = boardService.boardRead(boardId);
        String userId = board.getUserId();
        User user = userService.myPage(userId);


        //쿠키가 있을경우
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                // cookie의 name이 cookie + boardId와 일치하는 쿠키를 oldCookie에 넣어줌
                if (cookies[i].getName().equals("cookie" + boardId)) {
                    log.info("쿠키 처음 생성한 뒤 돌아옴");
                    oldCookie = cookies[i];
                }
            }
        }


        if (oldCookie == null) {
            //쿠키 생성
            Cookie newCookie = new Cookie("cookie" + boardId, "|" + boardId + "|");
            //쿠키 추가
            response.addCookie(newCookie);

            //쿠키를 추가 시키고 조회수를 증가
            int boardCountSet = boardService.boardCountSet(boardId);//조회수 증가
            model.addAttribute("boardCountSet", boardCountSet);

        } else { //oldCookie 가 null 아닌거 즉 쿠기가 있으므로 조회수 증가 로직을 처리 X
            String value = oldCookie.getValue();
            log.info(value);
        }


        model.addAttribute("board", board);
        model.addAttribute("user", user);

        return "bRead";
    }

    @GetMapping("/modify")
    public String boardModify(@RequestParam("boardId") int boardId, Model model) throws Exception {


        Board board = boardService.boardRead(boardId);

        model.addAttribute("board", board);


        return "bModify";
    }

    //게시글 수정
    @PostMapping("/modify")
    public String boardModifyForm(Board board, Model model, HttpSession session) throws Exception {
        //유효성 검사
        if (board.getUserId() == session.getAttribute("sUserId")) {

            boardService.boardModify(board);
            return "redirect:/read";
        } else {

            return "redirect:/login";
        }


    }


    //게시글 삭제
    @PostMapping("/remove")
    public String boardRemove(@RequestParam("boardId") int boardId
            , @RequestParam("userId") String userId
            , Model model) throws Exception {
        log.info("remove.....");

        boardService.boardRemove(boardId, userId);

        return "redirect:/main";
    }

    @GetMapping(value = "/uploadImg/{filename}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> showImage(@PathVariable String filename) throws Exception {
        log.info(filename);
        if (filename.toString().equals("") || filename == null) {
            filename = "NotImg";
        }
        log.info(filename);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(
                new File("C:\\uploadImg/" + filename))), header, HttpStatus.CREATED);
    }

    //동호회 이메일 신청
    @PostMapping("/applyEmail")
    @ResponseBody
    public void applyEmail(@RequestParam int boardId, Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();//이메일 신청자 세션으로 값 가져오기
        String applyIs = (String) session.getAttribute("applyIs");

        String toUser = boardService.boardRead(boardId).getUserId();//게시글 글쓴이 아이디
        String to = userService.myPage(toUser).getEmail();//게시글 글쓴이 이메일

        String userId = (String) session.getAttribute("sUserId");// 신청한 사람 아이디
//        String userId = "rox123";
        User user = userService.myPage(userId);
        String fromEmail = user.getEmail(); //신청한 사람 이메일
        String nickname = user.getNickname();//신청한 사람 닉네임
        String name = user.getName();//신청한 사람 닉네임
        String title = boardService.boardRead(boardId).getTitle();//신청한 게시글 제목


        if (applyIs != "true") {
            log.info("이메일 전송");
            session.setAttribute("applyIs", "true");
            model.addAttribute("applyIs", true);
            boardService.sendSimpleMessage(title, to, userId, fromEmail, nickname, name);
        } else {
            log.info("이미 이메일 전송");
        }


    }




}
