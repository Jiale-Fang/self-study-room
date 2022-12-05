package com.example.demo.dao;

import com.example.demo.pojo.Comment;
import com.example.demo.util.ChatLogTimeUtil;
import com.example.demo.util.JdbcUtil;
import com.example.demo.util.TimeUtil;
import com.example.demo.vo.CommentVO;
import com.example.demo.vo.MomentDetailVO;
import com.example.demo.vo.MomentsVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MomentsDao {

    public static void main(String[] args) {
        System.out.println(new MomentsDao().isThumbUp(1, 8));
    }


    public List<MomentsVO> getAllMoments() {
        try {
            List<MomentsVO> momentsVOList = new ArrayList<>();
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT m.id, m.title, m.likes, m.cover, m.create_time, u.username, u.avatar, COUNT(c.id) commentCnt " +
                    "FROM `user` u, moments m LEFT JOIN `comment` c " +
                    "ON c.mid = m.id " +
                    "WHERE m.uid = u.id " +
                    "GROUP BY m.id";
            PreparedStatement statement = connection.prepareCall(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MomentsVO momentsVO = new MomentsVO();
                momentsVO.setId(rs.getInt("id"));
                momentsVO.setUsername(rs.getString("username"));
                momentsVO.setTitle(rs.getString("title"));
                momentsVO.setLikes(rs.getInt("likes"));
                Timestamp timestamp = (Timestamp) rs.getObject("create_time");
                momentsVO.setCreateTime(TimeUtil.timestampToLocalDateTime(timestamp));
                momentsVO.setCover(rs.getString("cover"));
                momentsVO.setAvatar(rs.getString("avatar"));
                momentsVO.setCommentCnt(rs.getInt("commentCnt"));
                momentsVOList.add(momentsVO);
            }
            statement.close();
            connection.close();
            return momentsVOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isThumbUp(int userId, int momentId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "DELETE FROM favorites WHERE uid =? AND mid =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, momentId);
            int i = statement.executeUpdate();
//            //Delete 0 row, which means that the current user have not like this post before
            boolean isThumbUp = i == 0;
            if (isThumbUp) {
                sql = "INSERT INTO favorites(uid, mid) VALUES(?,?) ";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.setInt(2, momentId);
                statement.executeUpdate();

                sql = "UPDATE moments SET likes = likes + 1 WHERE id = ?";
                statement = connection.prepareStatement(sql);
            } else {
                sql = "UPDATE moments SET likes = likes - 1 WHERE id = ?";
                statement = connection.prepareStatement(sql);
            }
            statement.setInt(1, momentId);
            statement.executeUpdate();
            statement.close();
            connection.close();
            return isThumbUp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addComment(Comment comment) {
        //Get db connection
        Connection connection = JdbcUtil.getConnection();
        String sql = "INSERT INTO comment(mid, uid, content) values(?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, comment.getMomentId());
            statement.setInt(2, comment.getUid());
            statement.setString(3, comment.getContent());
            statement.executeUpdate();  //executeUpdate
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<CommentVO> getCommentList(int momentId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT c.id, c.content, c.create_time, u.username, u.avatar, c.uid " +
                    "FROM `comment` c, `user` u " +
                    "WHERE c.uid = u.id AND c.mid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, momentId);
            ResultSet resultSet = statement.executeQuery();
            List<CommentVO> commentVOList = new ArrayList<>();
            while (resultSet.next()) {
                CommentVO commentVO = new CommentVO();
                commentVO.setId(resultSet.getInt("id"));
                commentVO.setAvatar(resultSet.getString("avatar"));
                commentVO.setContent(resultSet.getString("content"));
                commentVO.setUsername(resultSet.getString("username"));
                commentVO.setUid(resultSet.getInt("uid"));
                LocalDateTime localDateTime = TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("create_time"));
                commentVO.setCreateTime(localDateTime);
                commentVO.setFormatTime(ChatLogTimeUtil.formatTime(localDateTime));
                commentVOList.add(commentVO);
            }

            statement.close();
            connection.close();
            return commentVOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getMomentDetail(int momentId, MomentDetailVO momentDetailVO) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT m.id, m.cover, m.create_time, u.username, u.avatar, m.title, u.id uid " +
                    "FROM moments m, `user` u " +
                    "WHERE m.uid = u.id AND m.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, momentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                momentDetailVO.setId(resultSet.getInt("id"));
                momentDetailVO.setAuthorId(resultSet.getInt("uid"));
                momentDetailVO.setAvatar(resultSet.getString("avatar"));
                momentDetailVO.setTitle(resultSet.getString("title"));
                momentDetailVO.setCover(resultSet.getString("cover"));
                momentDetailVO.setUsername(resultSet.getString("username"));
                LocalDateTime localDateTime = TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("create_time"));
                momentDetailVO.setCreateTime(localDateTime);
                momentDetailVO.setFormatTime(ChatLogTimeUtil.formatTime(localDateTime));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
