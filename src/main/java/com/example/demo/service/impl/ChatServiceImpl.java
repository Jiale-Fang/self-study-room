package com.example.demo.service.impl;

import com.example.demo.dao.ChatDao;
import com.example.demo.pojo.ChatLog;
import com.example.demo.service.ChatService;
import com.example.demo.vo.ChatVO;
import com.example.demo.vo.ChatCardVO;

import java.util.List;

public class ChatServiceImpl implements ChatService {

    private static final ChatDao chatDao = new ChatDao();

    @Override
    public void saveChatLog(ChatLog chatLog) {
        chatDao.saveChatLog(chatLog);
    }

    @Override
    public List<ChatVO> getGroupChatMessage() {
        return chatDao.getGroupMessage();
    }

    @Override
    public List<ChatVO> getPrivateChatMessage(int userId, int friendId) {
        return chatDao.getPrivateChatMessage(userId, friendId);
    }

    @Override
    public ChatCardVO getGroupChatCardInfo() {
        return chatDao.getGroupChatCardInfo();
    }

    @Override
    public boolean hasTalkedBefore(int userId, int friendId) {
        return chatDao.hasTalkedBefore(userId, friendId);
    }
}
