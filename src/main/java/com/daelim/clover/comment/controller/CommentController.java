package com.daelim.clover.comment.controller;

import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.comment.domain.Comment;
import com.daelim.clover.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    //댓글 등록
    @PostMapping(value = "/new",
        consumes =  "application/json",
        produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> register(@RequestBody Comment comment) throws Exception {

        ResponseEntity<String> entity = null;
        int insertCount = commentService.registerComment(comment);
//        entity = new ResponseEntity<String>("resSuccess", HttpStatus.OK);
//        entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        return insertCount == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 리스트 이게 지금 문제
        @GetMapping(value = "/pages/{boardId}/{pageNum}",
                produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Comment>> list(@PathVariable("pageNum") int pageNum, @PathVariable("boardId") int boardId) throws Exception {
        log.info("getList.........");
        Criteria  cri = new Criteria(pageNum,10);
        log.info("cri " + cri);
        return new ResponseEntity<>(commentService.getPagingList(cri,boardId),HttpStatus.OK);
    }

    //댓글 읽기
    @GetMapping(value = "/{commentId}",
                produces = {MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Comment> update(@PathVariable("commentId") int commentId, @RequestBody Comment comment) throws Exception {



       return new ResponseEntity<>(commentService.readComment(commentId), HttpStatus.OK);
    }
    //댓글 삭제
    @DeleteMapping(value = "/{commentId}",
                    produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> delete(@PathVariable("commentId") int commentId) throws Exception{

        return commentService.removeComment(commentId) == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 수정
    @RequestMapping(method = { RequestMethod.PATCH, RequestMethod.PUT},
                    value = "/{commentId}",
                    consumes = "application/json",
                    produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> modify(@RequestBody Comment comment, @PathVariable("commentId") int commentId)
            throws Exception{
        comment.setCommentId(commentId);

    log.info("update : " + commentId);
        return commentService.modifyComment(comment) == 1
                ? new ResponseEntity<>("success",HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
