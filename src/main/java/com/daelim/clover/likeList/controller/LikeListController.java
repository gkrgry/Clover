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
@RequestMapping("/likeList")
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
        if(userId !="" || userId != null){//유효성
            likeListService.insertLikeList(boardId,userId,grade);
        }else{
            model.addAttribute("msg","로그인 해주세요");
        }

    }

    //별점 있는지 없는지 조회
    @GetMapping("/selectLike")
    @ResponseBody
    public int selectLikeList(@RequestParam int boardId, Model model, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        //로그인한 아이디
        String userId = (String)session.getAttribute("sUserId");
        if(userId == null){
            userId = "";
        }
//        String userId = "qwe";
        return likeListService.selectLikeList(boardId,userId);
    }

    //있다면 넣은 점수 가져오기
    @GetMapping("/selectLikeGrade")
    @ResponseBody
    public int selectLikeListGrade(@RequestParam int boardId, Model model, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        //로그인한 아이디
        String userId = (String)session.getAttribute("sUserId");
        if(userId == null){
            userId = "";
        }
//        String userId = "qwe";
        return likeListService.selectLikeListGrade(boardId,userId);
    }

    //평균 점수 가져오기
    @GetMapping("/likeListAvg")
    @ResponseBody
    public int likeListAvg(@RequestParam int boardId) throws Exception{
        return Math.round(likeListService.likeListAvg(boardId)) ;
    }

    // 점수 수정
    @PutMapping("/updateLikeList")
    @ResponseBody
    public void updateLikeList(@RequestParam int boardId,HttpServletRequest request ,Model model
            ,@RequestParam int grade) throws Exception{

        HttpSession session = request.getSession();
        //로그인한 아이디
        String userId = (String)session.getAttribute("sUserId");
//        String userId = "asd";
        if(userId !="" || userId != null){//유효성
            likeListService.updateLikeList(userId,boardId,grade);
        }else{
            model.addAttribute("msg","로그인 해주세요");
        }

    }

    // 점수 삭제
    @DeleteMapping("/deleteLikeList")
    @ResponseBody
    public void deleteLikeList(@RequestParam int boardId,Model model
            ,HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        //로그인한 아이디

        String userId = (String)session.getAttribute("sUserId");
//        String userId = "asd";
        if(userId !="" || userId != null){//유효성
            likeListService.deleteLikeList(boardId,userId);
        }else{
            model.addAttribute("msg","로그인 해주세요");
        }

    }

}
