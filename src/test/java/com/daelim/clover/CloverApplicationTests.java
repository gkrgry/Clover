package com.daelim.clover;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.board.mapper.BoardMapper;
import com.daelim.clover.board.service.BoardService;
import com.daelim.clover.comment.domain.Comment;
import com.daelim.clover.comment.mapper.CommentMapper;
import groovy.util.logging.Log4j2;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
class CloverApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloverApplicationTests.class);

    @Autowired
    private BoardMapper mapper;

    @Autowired
    BoardService service;


    @Test
    void contextLoads() {
    }



    @Test
    public void commentTest(){

    }


    @Test
    @DisplayName("insert test")
    public void testInsert(){

        IntStream.rangeClosed(51,200).forEach(i ->{
            Board board = new Board();
            board.setIndexId(1000);
            board.setTitle("제목 " + i);
            board.setContent("컨텐츠 " + i);
            board.setGenAddress("경기 안양시 동안구 임곡로 ");
            board.setDetAddress("상세 주소 " + i);
            board.setWeek("실험 " + i);
            board.setTagBigName("큰태그 " + i);
            board.setTagSmallName("작은태그 " + i);
            try{
                service.boardRegister(board);
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("board " + i );
        });


    }
    @Test
    @DisplayName("insert test2")
    public void testInsert2(){

        IntStream.rangeClosed(1,100).forEach(i ->{
            Board board = new Board();

            board.setLat(37.403289794921875);
            board.setLon(126.9306869506836);
            try{
                service.boardModify(board);
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("board " + i );
        });


    }
    @Autowired
    CommentMapper mapper2;
    @Test
    @DisplayName("insert comment test2")
    public void testInsertC(){

        IntStream.rangeClosed(1,10).forEach(i ->{
            Comment comment = new Comment();

            comment.setBoardId(227);
            comment.setIndexId(1000);
            comment.setContent("테스트 코드 : " +i);

            try{
                mapper2.insertComment(comment);
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("comment " + i );
        });


    }

    @Test
    @DisplayName("del comment test2")
    public void testDelC(){


            try{
                mapper2.deleteComment(1);

            } catch (Exception e){
                e.printStackTrace();
            }


    }

    @Test
    @DisplayName("del comment test2")
    public void testUpdC(){
        int i = 2;

        try{
            Comment comment = mapper2.selectComment(i);
            comment.setContent("수정");
            int count = mapper2.updateComment(comment);

            System.out.println(count);

        } catch (Exception e){
            e.printStackTrace();
        }


    }
    @Test
    @DisplayName("paging comment test2")
    public void testPaingingC(){

        Criteria cri = new Criteria();
            try{
                List<Comment> comments = mapper2.commentPagingList(cri,227);
                System.out.println("comment " + comments );
            } catch (Exception e){
                e.printStackTrace();
            }




    }


}
