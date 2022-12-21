package com.example.demo.service.impl;

import com.example.demo.dao.MomentsDao;
import com.example.demo.pojo.Comment;
import com.example.demo.service.MomentsService;
import com.example.demo.vo.CommentVO;
import com.example.demo.vo.MomentDetailVO;
import com.example.demo.vo.MomentsVO;

import java.util.List;

public class MomentsServiceImpl implements MomentsService {

    private static final MomentsDao momentsDao = new MomentsDao();

    public List<MomentsVO> getAllMoments(){
        return momentsDao.getAllMoments();
    }

    @Override
    public boolean isThumbUp(int userId, int momentId) {
        return momentsDao.isThumbUp(userId, momentId);
    }

    @Override
    public MomentDetailVO getMomentDetail(int momentId) {
        MomentDetailVO momentDetailVO = new MomentDetailVO();
        List<CommentVO> commentVOList = momentsDao.getCommentList(momentId);
        momentsDao.getMomentDetail(momentId, momentDetailVO);
        momentDetailVO.setCommentVOList(commentVOList);
        return momentDetailVO;
    }

    @Override
    public void addComment(Comment comment) {
        momentsDao.addComment(comment);
    }
}
