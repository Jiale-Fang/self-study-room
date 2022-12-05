package com.example.demo.dao;

import com.example.demo.pojo.Task;
import com.example.demo.util.JdbcUtil;
import com.example.demo.util.TimeUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class TaskDao {

    public int insertTask(Task task) {
        //Get db connection
        Connection connection = JdbcUtil.getConnection();
//        String sql = "insert into task(tid, uid, room_id, create_time, expire_time, is_finished) values(?,?,?,?,?,?)";
        String sql = "insert into task(uid, room_id, create_time, expire_time, is_finished, goal) values(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, task.getUid());
            statement.setInt(2, task.getRoomId());
            statement.setObject(3, task.getCreateTime());
            statement.setObject(4, task.getExpireTime());
            statement.setBoolean(5, task.isFinished());
            statement.setString(6, task.getGoal());
            int i = statement.executeUpdate();  //executeUpdate
            if (i <= 0) {
                throw new RuntimeException("Insert tasks fail!");
            }
            ResultSet rs = statement.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            statement.close();
            connection.close();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishTask(Integer taskId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "Update task set is_finished = ? where id = ?";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setBoolean(1, true);
            statement.setInt(2, taskId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
