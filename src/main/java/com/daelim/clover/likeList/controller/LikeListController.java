package com.daelim.clover.likeList.controller;

import com.daelim.clover.likeList.domain.LikeList;
import com.daelim.clover.likeList.service.LikeListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/listList")
public class LikeListController {

    private final LikeListService likeListService;

    //점수 입력
    @PostMapping("/insertLike")
    @ResponseBody
    public void insertLikeList(@RequestParam int boardId, Model model, HttpServletRequest request
    , int grade) throws Exception{
        HttpSession session = request.getSession();
        //로그인한 아이디
        String userId = (String)session.getAttribute("sUserId");
//        String userId = "qwe";
        likeListService.insertLikeList(boardId,userId,grade);
    }

    //select
    @GetMapping("/selectLike")
    @ResponseBody
    public void selectLikeList(@RequestParam int boardId, Model model, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        //로그인한 아이디
        String userId = (String)session.getAttribute("sUserId");
        LikeList likeList = likeListService.selectLikeList(boardId,userId);
    }

    //평균 점수 가져오기
    @GetMapping("/likeListAvg")
    @ResponseBody
    public int likeListAvg(@RequestParam int boardId) throws Exception{
        return likeListService.likeListAvg(boardId);
    }

    // 점수 수정
    @PutMapping("/updateLikeList")
    @ResponseBody
    public void updateLikeList(@RequestParam int boardId,HttpServletRequest request ,@RequestParam int grade) throws Exception{

        HttpSession session = request.getSession();
        //로그인한 아이디
        String userId = (String)session.getAttribute("sUserId");
//        String userId = "asd";
        likeListService.updateLikeList(userId,boardId,grade);
    }

    // 점수 삭제
    @DeleteMapping("/deleteLikeList")
    @ResponseBody
    public void deleteLikeList(@RequestParam int boardId,HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        //로그인한 아이디
//        String userId = (String)session.getAttribute("sUserId");
        String userId = "asd";
        likeListService.deleteLikeList(boardId,userId);
    }

}
