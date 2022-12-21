package com.example.demo.chat;

import java.net.*;
import java.io.*;

public class Client {
    private static final long serialVersionUID = 1L;
    public final ObjectInputStream ois;
    public final ObjectOutputStream oos;

    public Client(int userId) throws Exception, IOException {
        Socket socket = new Socket("127.0.0.1", 6200); // Connect server
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(userId);
    }

    public static void main(String args[]) throws IOException, Exception {
//        new Client();
    }
}



