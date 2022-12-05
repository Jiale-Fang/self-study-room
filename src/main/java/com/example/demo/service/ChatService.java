package com.example.demo.service;

import com.example.demo.pojo.ChatLog;
import com.example.demo.vo.ChatVO;
import com.example.demo.vo.ChatCardVO;

import java.util.List;

public interface ChatService {

    void saveChatLog(ChatLog chatLog);

    List<ChatVO> getGroupChatMessage();

    List<ChatVO> getPrivateChatMessage(int userId, int friendId);

    ChatCardVO getGroupChatCardInfo();

    boolean hasTalkedBefore(int userId, int friendId);
}
