package com.daelim.clover;

import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.board.mapper.BoardMapper;
import com.daelim.clover.board.service.BoardService;
import groovy.util.logging.Log4j2;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@Log4j2
@SpringBootTest
class CloverApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloverApplicationTests.class);

    @Autowired
    BoardMapper mapper;

    @Autowired
    BoardService service;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsert(){


    }

}
