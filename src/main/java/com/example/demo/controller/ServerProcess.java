package com.example.demo.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerProcess {
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/studyroom?&useSSL=false&serverTimezone=UTC";
    String user = "root";
    String password = "kllxdwh200021";

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
        int score = -1;
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
        if(currentScore >= 0) {
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

    public static void signUp(String uid) {     // invoke when create a new account
        ServerProcess sp = ServerProcess.getServer();

        // table score
        try(Statement statement = sp.connection.createStatement()){
            statement.execute("INSERT INTO score VALUES ('" + uid + "','" + 0 + "','" + 0 + "')");
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }

        // table distribution
        ArrayList<String> listFields = new ArrayList<>();
        try(Statement statement = sp.connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT DISTINCT field FROM distribution");
            while(rs.next())
                listFields.add(rs.getString("field"));
            for(String field: listFields){
                statement.execute("INSERT INTO distribution VALUES ('" + uid + "','" + field + "','" + 0 + "')");
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }

        // table farm
        try(Statement statement = sp.connection.createStatement()){
            statement.execute("INSERT INTO farm VALUES ('" + uid + "','" + "lemon" + "','" + 0 + "')");
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }

        // table fruit
        ArrayList<String> listFruits = new ArrayList<>();
        try(Statement statement = sp.connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT DISTINCT fruit FROM fruit");
            while(rs.next())
                listFruits.add(rs.getString("fruit"));
            for(String fruit: listFruits)
                statement.execute("INSERT INTO fruit VALUES ('" + uid + "','" + fruit + "','" + 0 + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logOut(String uid){       // invoke when log out
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT real_time FROM studytime WHERE uid='" + uid + "'");
            rs.next();
            int time = rs.getInt("real_time");
            ServerProcess.server.updateScoreAccumulation(uid, time / 10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void synchronizeTables(){ // synchronize data for tables in database
        ArrayList<String> listUid = new ArrayList<>();
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT DISTINCT id from user");
            while(rs.next())
                listUid.add(rs.getString("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(String uid: listUid)
            signUp(uid);
    }
}

