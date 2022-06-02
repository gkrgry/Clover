package com.daelim.clover.likeList.service;

import com.daelim.clover.likeList.domain.LikeList;

public interface LikeListService {
    public void insertLikeList(Integer boardId, String userId, Integer grade) throws Exception;

    public LikeList selectLikeList(Integer boardId, String userId) throws Exception;

    public int likeListAvg(Integer boardId) throws Exception;

    public int updateLikeList(String userId,Integer boardId ,Integer grade) throws Exception;

    public void deleteLikeList(Integer boardId, String userId) throws Exception;

    public void userLikeAllDelete(String userId) throws Exception;

}
