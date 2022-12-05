package com.example.demo.service;

import com.example.demo.pojo.Comment;
import com.example.demo.vo.MomentDetailVO;
import com.example.demo.vo.MomentsVO;

import java.util.List;

public interface MomentsService {
    List<MomentsVO> getAllMoments();

    boolean isThumbUp(int userId, int momentId);

    MomentDetailVO getMomentDetail(int momentId);

    void addComment(Comment comment);
}
