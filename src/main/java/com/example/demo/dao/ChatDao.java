package com.example.demo.dao;

import com.example.demo.pojo.ChatLog;
import com.example.demo.util.ChatLogTimeUtil;
import com.example.demo.util.JdbcUtil;
import com.example.demo.util.TimeUtil;
import com.example.demo.vo.ChatVO;
import com.example.demo.vo.ChatCardVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatDao {
    public void saveChatLog(ChatLog chatLog) {
        //Get db connection
        Connection connection = JdbcUtil.getConnection();
        String sql = "INSERT INTO chat_log(sender, content, text_type, receiver) values(?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, chatLog.getSender());
            statement.setString(2, chatLog.getContent());
            statement.setInt(3, chatLog.getTextType());
            statement.setObject(4, chatLog.getReceiver());
            statement.executeUpdate();  //executeUpdate
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ChatVO> getGroupMessage() {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT c.id, c.sender, u.username as senderName, c.content, c.create_time, u.avatar " +
                "FROM `user` u, chat_log c " +
                "WHERE u.id = c.sender AND text_type = 1";
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            ResultSet resultSet = statement.executeQuery();
            List<ChatVO> chatVOList = new ArrayList<>();
            while (resultSet.next()) {
                ChatVO chatVO = new ChatVO();
                chatVO.setId(resultSet.getInt("id"));
                chatVO.setSender(resultSet.getInt("sender"));
                chatVO.setAvatar(resultSet.getString("avatar"));
                chatVO.setContent(resultSet.getString("content"));
                chatVO.setSenderName(resultSet.getString("senderName"));
                Timestamp createTime = resultSet.getTimestamp("create_time");
                chatVO.setCreateTime(TimeUtil.timestampToLocalDateTime(createTime));
                chatVOList.add(chatVO);
            }
            statement.close();
            connection.close();
            return chatVOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ChatVO> getPrivateChatMessage(int userId, int friendId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT c.id, u.username senderName, u.avatar, c.content, c.create_time, u.id sender " +
                    "FROM user u, chat_log c " +
                    "WHERE ((c.sender = ? AND c.receiver = ? AND u.id = sender ) OR (c.sender = ? AND c.receiver = ? AND u.id = sender)) AND c.text_type = 2 " +
                    "ORDER BY c.create_time ASC";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            statement.setInt(2, friendId);
            statement.setInt(3, friendId);
            statement.setInt(4, userId);
            ResultSet resultSet = statement.executeQuery();
            List<ChatVO> privateChatVOList = new ArrayList<>();
            while (resultSet.next()) {
                ChatVO ChatVO = new ChatVO();
                ChatVO.setId(resultSet.getInt("id"));
                ChatVO.setAvatar(resultSet.getString("avatar"));
                ChatVO.setSenderName(resultSet.getString("senderName"));
                ChatVO.setContent(resultSet.getString("content"));
                ChatVO.setCreateTime(TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("create_time")));
                ChatVO.setSender(resultSet.getInt("sender"));
                privateChatVOList.add(ChatVO);
            }
            return privateChatVOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatCardVO getGroupChatCardInfo() {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT id, content, create_time" +
                    " FROM chat_log" +
                    " WHERE text_type = 1" +
                    " ORDER BY create_time DESC" +
                    " LIMIT 1";
            PreparedStatement statement = connection.prepareCall(sql);
            ResultSet resultSet = statement.executeQuery();
            ChatCardVO chatCardVO = new ChatCardVO();
            while (resultSet.next()) {
                chatCardVO.setId(resultSet.getInt("id"));
                chatCardVO.setContent(resultSet.getString("content"));
                LocalDateTime createTime = TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("create_time"));
                chatCardVO.setFormatTime(ChatLogTimeUtil.formatTime(createTime));
            }
            return chatCardVO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasTalkedBefore(int userId, int friendId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT count(id) count " +
                    "FROM chat_log " +
                    "WHERE text_type = 2 AND ((sender = ? AND receiver= ?) OR (sender = ? AND receiver= ?))";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            statement.setInt(2, friendId);
            statement.setInt(3, friendId);
            statement.setInt(4, userId);
            ResultSet resultSet = statement.executeQuery();
            boolean flag = false;
            while (resultSet.next()) {
                flag = resultSet.getInt("count") >= 1;
            }
            return flag;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
