package com.example.demo.chat;

import com.example.demo.constant.MessageTypeConstant;
import com.example.demo.entity.Message;
import com.example.demo.pojo.Seat;
import com.example.demo.thread.ServerThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Server {

    //Store seats information of the study room
    public static Map<Integer, Seat> seatMap = new HashMap<>();
    //    public static Queue<GroupServerThread> clientThread = new LinkedList<>();
    public static Map<Integer, ServerThread> clientThreadMap = new HashMap<>();
    //Restrict maximum online people
    public LFUCache lfuCache = new LFUCache(100);

    public Server() {
        try {
            initializeAllSeats();
            ServerSocket ss = new ServerSocket(6200);
            while (true) {
                Socket severSocket = ss.accept(); // wait for client
                ObjectOutputStream oos = new ObjectOutputStream(severSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(severSocket.getInputStream());
                int userId = (int) ois.readObject();
                ServerThread serverThread = new ServerThread(oos, ois);
                //Add to client thread list
                clientThreadMap.put(userId, serverThread);
                serverThread.start();

                //Share latest seats info to every client
                Message message = new Message(MessageTypeConstant.ALL_SEAT_INFO, Server.seatMap);
                message.setSenderId(userId);

                //Remove inactive user
                int removeId = lfuCache.put(userId);
                if (removeId != -1) {
                    updateSeat(removeId);
                    oos.writeObject(message);
                    removeClient(removeId);
                }else {
                    oos.writeObject(message);
                }
                System.out.println("A user comes in, his(her) id is: " + userId);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSeat(int removeId){
        for (int i = 1; i <= 18; i++) {
            Seat seat = seatMap.get(i);
            if (seat.getUserId() == removeId){
                if (seat.isSitDown()){
                    seat.setUserId(0);
                    seat.setSitDown(false);
                    seatMap.put(i, seat);
                }
                break;
            }
        }
    }

    private void removeClient(int removeId) {
        try {
            //notify the remove client
            Message messageRemove = new Message(MessageTypeConstant.REMOVE_INFO, removeId);
            ServerThread removeST = clientThreadMap.get(removeId);
            removeST.oos.writeObject(messageRemove);
            clientThreadMap.remove(removeId);
            removeST.interrupt();
            removeST.oos.close();
            removeST.ois.close();
            System.out.println("Remove one user, his(her) id is: " + removeId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeAllSeats() {
        for (int i = 1; i <= 18; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSitDown(false);
            seatMap.put(i, seat);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

