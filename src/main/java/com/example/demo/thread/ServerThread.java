package com.example.demo.thread;

import com.example.demo.chat.Server;
import com.example.demo.constant.MessageTypeConstant;
import com.example.demo.entity.Message;
import com.example.demo.pojo.Seat;
import com.example.demo.vo.UserChatVO;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * This thread handles the connections with the corresponding user and sends the data from server to other users
 */
public class ServerThread extends Thread {
    //    int id; //
    ObjectOutputStream oos; // To user
    ObjectInputStream ois; // From user

    public ServerThread(ObjectOutputStream oos, ObjectInputStream ois) {
//        this.id = id;
        this.oos = oos;
        this.ois = ois;
    }

    public void run() {
        //Always monitor user's incoming message and transfer it to other users
        while (true) {
            try {
                Message message = (Message) ois.readObject();
                if (message.getType() == MessageTypeConstant.PRIVATE_CHAT_MESSAGE){
                    UserChatVO userChatVO = (UserChatVO)message.getObject();
                    //Get the receiver's thread
                    ServerThread serverThread = Server.clientThreadMap.get(userChatVO.getReceiver());
                    //Only send the message to the receiver
                    serverThread.oos.writeObject(message);
                    System.out.println("˽����Ϣ"+message);
                    continue;
                }

                if (message.getType() == MessageTypeConstant.ONE_SEAT_INFO){
                    updateSeatMap(message.getObject());
                }

                for (Map.Entry<Integer, ServerThread> entry : Server.clientThreadMap.entrySet()) {
                    //Forward message to all users
                    entry.getValue().oos.writeObject(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateSeatMap(Object object){
        Seat seat = (Seat) object;
        Server.seatMap.put(seat.getSeatId(), seat);
    }
}
