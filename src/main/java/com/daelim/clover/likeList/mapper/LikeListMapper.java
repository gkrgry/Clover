package com.daelim.clover.likeList.mapper;

import com.daelim.clover.likeList.domain.LikeList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeListMapper {
    public void insertLikeList(Integer boardId, String userId, Integer grade) throws Exception;

    public int selectLikeList(Integer boardId, String userId) throws Exception;

    public int selectLikeListGrade(Integer boardId, String userId) throws Exception;

    public int likeListAvg(Integer boardId) throws Exception;

    public int updateLikeList(String userId,Integer boardId, Integer grade) throws Exception;

    public void deleteLikeList(Integer boardId, String userId) throws Exception;

    public void userLikeAllDelete(String userId) throws Exception;
}
