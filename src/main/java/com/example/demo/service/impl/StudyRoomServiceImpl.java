package com.example.demo.service.impl;

import com.example.demo.dao.StudyRoomDao;
import com.example.demo.dao.TaskDao;
import com.example.demo.pojo.Seat;
import com.example.demo.pojo.Task;
import com.example.demo.service.StudyRoomService;

import java.util.List;

public class StudyRoomServiceImpl implements StudyRoomService {

    private final static StudyRoomDao studyRoomDao = new StudyRoomDao();
    private final static TaskDao taskDao = new TaskDao();

    @Override
    public int startTask(Task task) {
        return taskDao.insertTask(task);
    }

    @Override
    public boolean finishTask(Integer taskId) {
//        studyRoomDao.sitOrStand(seat.getSeatId(), seat.isSitDown());
        taskDao.finishTask(taskId);
        return false;
    }

    public List<Seat> getAllSeats(){
        return studyRoomDao.getAllSeats();
    }
}
