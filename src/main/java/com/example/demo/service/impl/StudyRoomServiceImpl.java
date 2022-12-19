package com.example.demo.service.impl;

import com.example.demo.dao.StudyRoomDao;
import com.example.demo.pojo.Seat;
import com.example.demo.service.StudyRoomService;

import java.util.List;

public class StudyRoomServiceImpl implements StudyRoomService {

    private final static StudyRoomDao studyRoomDao = new StudyRoomDao();

    public List<Seat> getAllSeats(){
        return studyRoomDao.getAllSeats();
    }
}
