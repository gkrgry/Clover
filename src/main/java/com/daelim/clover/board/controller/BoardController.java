package com.daelim.clover.board.controller;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class BoardController {



    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public String boardList(Model model) throws Exception{
        log.info("List 입니다");

        List<Board> boardList = boardService.boardList();

        model.addAttribute("boardList", boardList);

        return "bList";
    }


    @GetMapping("/register")
    public String boardRegisterForm(Board board, Model model) throws Exception{
        log.info("get 입력 입니다.");


        return "bRegister";
    }
    @GetMapping("/read")
    public String boardRead(@RequestParam("boardId") int boardId, Model model) throws Exception{
        log.info("read 입력 입니다.");
        Board board = boardService.boardRead(boardId);

        model.addAttribute("board",board);


        return "bRead";
    }

    @PostMapping("/register")
    public String boardRegister(Board board, Model model) throws Exception{
        log.info("post 입력 입니다.");
        boardService.boardRegister(board);
        model.addAttribute("msg", "성공했스니다.");

        return "success";
    }
}
