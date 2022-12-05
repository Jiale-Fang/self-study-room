package com.example.demo.service;

import com.example.demo.pojo.Seat;
import com.example.demo.pojo.Task;

import java.util.List;

public interface StudyRoomService {

    int startTask(Task task);

    boolean finishTask(Integer taskId);

    List<Seat> getAllSeats();
}
