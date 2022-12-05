package com.example.demo.controller;

import java.sql.*;
import java.util.HashMap;

public class ServerProcess {
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/studyroom?&useSSL=false&serverTimezone=UTC";
    String user = "root";
    String password = "your password";

    static ServerProcess server = null;

    private ServerProcess(){
        init();
    }

    public void init(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException exception){
            exception.printStackTrace();
            System.exit(0);
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException exception){
            exception.printStackTrace();
            System.exit(0);
        }
    }

    /* ---------- API ---------- */

    public static ServerProcess getServer(){
        if(ServerProcess.server == null)
            ServerProcess.server = new ServerProcess();
        return ServerProcess.server;
    }

    public int getScoreAccumulation(String uid){
        String sql = "SELECT accumulation FROM score WHERE uid='" + uid + "'";
        int accumulation = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            accumulation = result.getInt("accumulation");
            result.close();
            statement.close();
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
        return accumulation;
    }

    public int getScoreReputation(String uid){
        String sql = "SELECT reputation FROM score WHERE uid='" + uid + "'";
        int reputation = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            reputation = result.getInt("reputation");
            result.close();
            statement.close();
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
        return reputation;
    }

    public void updateScoreAccumulation(String uid, int score){
        score += getScoreAccumulation(uid);
        String sql = "UPDATE score SET accumulation='" + score + "' WHERE uid='" + uid + "'";
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public void updateScoreReputation(String uid, int score){
        score += getScoreReputation(uid);
        String sql = "UPDATE score SET reputation='" + score + "' WHERE uid='" + uid + "'";
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (SQLException exception){
            exception.printStackTrace();
            System.exit(0);
        }
    }

    public void createAccount(String uid){
        String sql = "INSERT INTO score VALUES ('" + uid + "','" + 0 + "','" + 0 + "')";
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (SQLException exception){
            exception.printStackTrace();
            System.exit(0);
        }
    }

    public int getLevel(int score){
        return (int)((1 + Math.sqrt(1 + 1.6 * score)) / 2);
    }

    public int scoreToNext(int score, int level){
        return 5 * level * (level + 1) / 2 - score;
    }

    public HashMap<String, Integer> getDistribution(String uid){    // HashMap<uid, score>
        String sql = "SELECT * FROM distribution WHERE uid='" + uid + "'";
        HashMap<String, Integer> map = new HashMap<>();
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(sql);
            while(result.next())
                map.put(result.getString("field"), result.getInt("score"));
            result.close();
        }
        catch(SQLException exception){
            exception.printStackTrace();
            System.exit(0);
        }
        return map;
    }

    public int getScoreField(String uid, String field){
        String sql = "SELECT score FROM distribution WHERE uid='" + uid + "' AND field='" + field + "'";
        int score = 0;
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                score = result.getInt("score");
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
        return score;
    }

    public void updateDistribution(String uid, String field, int score){
        int currentScore = getScoreField(uid, field);
        String sql;
        if(currentScore > 0) {
            score += currentScore;
            sql = "UPDATE distribution SET score='" + score + "' WHERE uid='" + uid + "' AND field='" + field + "'";
        }
        else
            sql = "INSERT INTO distribution VALUES ('" + uid + "', '" + field + "', '" + score + "')";

        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
    }
}

