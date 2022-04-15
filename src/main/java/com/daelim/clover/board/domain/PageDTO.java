package com.daelim.clover.board.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

    private int startPage;      // 현재 화면에서 보이는 startPage 번호
    private int endPage;        // 현재 화면에 보이는 endPage 번호
    private boolean prev;       // 페이징 이전 버튼 활성화 여부
    private boolean next;       // 페이징 다음 버튼 활서화 여부


    private int total;     // 게시판 전체 데이터 개수
    private Criteria cri;       // 앞서 생성한 Criteria를 주입받는다.

    public PageDTO(Criteria cri, int total){
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0))* 10;
        this.startPage = this.endPage -9;
        int realEnd = (int) (Math.ceil(total * 1.0)) / cri.getAmount();

        if(realEnd < this.endPage){
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}
