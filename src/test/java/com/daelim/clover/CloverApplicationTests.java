package com.daelim.clover;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.board.mapper.BoardMapper;
import com.daelim.clover.board.service.BoardService;
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

}
