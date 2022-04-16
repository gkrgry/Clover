package com.daelim.clover.comment.controller;

import com.daelim.clover.comment.domain.Comment;
import com.daelim.clover.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/read")
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody Comment comment) {

        ResponseEntity<String> entity = null;
        try {
            commentService.registerComment(comment);
            entity = new ResponseEntity<String>("resSuccess", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    //댓글 리스트
    @GetMapping("/all/{boardId}")
    public ResponseEntity<List<Comment>> list(@PathVariable("board_id") Integer boardId) {

        ResponseEntity<List<Comment>> entity = null;
        try {
            entity = new ResponseEntity<List<Comment>>(commentService.getList(boardId), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<String> update(@PathVariable("comment_id") Integer commentId, @RequestBody Comment comment) {

        ResponseEntity<String> entity = null;
        try {
            comment.setCommentId(commentId);
            commentService.modifyComment(comment);
            entity = new ResponseEntity<String>("modSuccess", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(@PathVariable("comment_id") Integer commentId,
                                         @PathVariable("board_id") Integer boardId,
                                         @PathVariable("index_id") Integer indexId) {

        ResponseEntity<String> entity = null;
        try {

            commentService.removeComment(commentId);
            entity = new ResponseEntity<String>("delSuccess", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return entity;
    }



}
