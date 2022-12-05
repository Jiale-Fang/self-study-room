package com.example.demo.dao;

import com.example.demo.pojo.Seat;
import com.example.demo.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudyRoomDao {
    public List<Seat> getAllSeats() {
        try {
            List<Seat> seatList = new ArrayList<>();
            Connection connection = JdbcUtil.getConnection();
            String sql = "select * from seat";
            PreparedStatement statement = connection.prepareCall(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("id"));
                seat.setSitDown(rs.getInt("is_sit_down") == 1);
                seatList.add(seat);
            }
            connection.close();
            return seatList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sitOrStand(Integer id, boolean isSitDown) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "Update seat set is_sit_down = ? where id = ?";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, isSitDown ? 1 : 0);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
