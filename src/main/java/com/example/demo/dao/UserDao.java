package com.example.demo.dao;

import com.example.demo.pojo.User;
import com.example.demo.util.JdbcUtil;
import com.example.demo.util.TimeUtil;
import com.example.demo.vo.ContactVO;
import com.example.demo.vo.UserCardVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao {

    public static void main(String[] args) {
        System.out.println(new UserDao().getUserInfo(2).toString());
    }

    public String getUserGender(Integer userId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT gender FROM user u where u.id = ?";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String gender = null;
            while (rs.next()) {
                gender = (rs.getString("gender"));
            }
            connection.close();
            return gender;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserCardVO getUserCardInfo(Integer userId) {
        try {
            Connection connection = JdbcUtil.getConnection();

            String sql = "(SELECT DISTINCT u.id, u.username, u.avatar, u.gender, u.createtime as create_time " +
                    ", SUM(TIMESTAMPDIFF(MINUTE, create_time, expire_time)) as cumulative_study_time " +
                    "From task t, user u " +
                    "WHERE t.is_finished = 1 AND t.uid = ? AND u.id = t.uid) " +
                    "UNION " +
                    "(SELECT u.id, u.username, u.avatar, u.gender, u.createtime as create_time, 0 " +
                    "FROM `user` u WHERE id = ?) ";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            UserCardVO userCardVO = null;
            while (rs.next()) {
                if ((rs.getInt("id") == 0)) continue;
                userCardVO = new UserCardVO();
                userCardVO.setId(rs.getInt("id"));
                userCardVO.setCreateTime(rs.getDate("create_time"));
                userCardVO.setGender(rs.getString("gender"));
                userCardVO.setUsername(rs.getString("username"));
                userCardVO.setAvatar(rs.getString("avatar"));
                userCardVO.setCumulativeStudyTime(rs.getInt("cumulative_study_time"));
                break;
            }
            connection.close();
            return userCardVO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserInfo(int userId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(userId);
                user.setAvatar(resultSet.getString("avatar"));
                user.setGender(resultSet.getString("gender"));
                user.setUsername(resultSet.getString("username"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ContactVO> getContactInfo(int userId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "(SELECT c.id, uu.avatar, uu.username, c.create_time, c.content, uu.id friend_id " +
                    "FROM chat_log c, user u, user uu " +
                    "WHERE c.sender = u.id AND u.id = ? AND c.text_type = 2 AND uu.id = c.receiver) " +
                    "UNION " +
                    "(SELECT c.id, uu.avatar, uu.username, c.create_time, c.content, uu.id frenid_id " +
                    "FROM chat_log c, user u, user uu " +
                    "WHERE c.receiver = u.id AND u.id = ? AND c.text_type = 2 AND uu.id = c.sender) " +
                    "ORDER BY create_time DESC ";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            List<ContactVO> contactVOList = new ArrayList<>();
            while (resultSet.next()) {
                ContactVO contactVO = new ContactVO();
                contactVO.setId(resultSet.getInt("id"));
                contactVO.setAvatar(resultSet.getString("avatar"));
                contactVO.setUsername(resultSet.getString("username"));
                contactVO.setContent(resultSet.getString("content"));
                contactVO.setFriendId(resultSet.getInt("friend_id"));
                contactVO.setCreateTime(TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("create_time")));
                contactVOList.add(contactVO);
            }
            return contactVOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
