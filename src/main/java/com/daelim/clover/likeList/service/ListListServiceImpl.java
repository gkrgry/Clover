package com.daelim.clover.likeList.service;


import com.daelim.clover.likeList.domain.LikeList;
import com.daelim.clover.likeList.mapper.LikeListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListListServiceImpl implements LikeListService {

    private final LikeListMapper likeListMapper;

    @Override
    public void insertLikeList(Integer boardId, String userId, Integer grade) throws Exception {
        likeListMapper.insertLikeList(boardId,userId,grade);
    }

    @Override
    public int selectLikeList(Integer boardId, String userId) throws Exception {
        return likeListMapper.selectLikeList(boardId,userId);
    }

    @Override
    public int selectLikeListGrade(Integer boardId, String userId) throws Exception {
        return likeListMapper.selectLikeListGrade(boardId,userId);
    }

    @Override
    public int likeListAvg(Integer boardId) throws Exception {
        return likeListMapper.likeListAvg(boardId);
    }

    @Override
    public int updateLikeList(String userId,Integer boardId, Integer grade) throws Exception {
        return likeListMapper.updateLikeList(userId,boardId,grade);
    }

    @Override
    public void deleteLikeList(Integer boardId, String userId) throws Exception {
        likeListMapper.deleteLikeList(boardId, userId);
    }

    @Override
    public void userLikeAllDelete(String userId) throws Exception {
        likeListMapper.userLikeAllDelete(userId);
    }
}
