package com.example.demo.controller;

import com.example.demo.DiagramApplication;
import com.example.demo.pojo.StudyTime;
import com.example.demo.util.JdbcUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiagramController implements Initializable {

    @FXML
    public PieChart pieChart;
    @FXML
    Label caption;

    public void init() throws Exception {

        java.sql.Connection conn = JdbcUtil.getConnection();

        String sql = "select * from studytime where uid = 1";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        ArrayList<StudyTime> list = new ArrayList<>();

        while (rs.next()) {
            StudyTime st = new StudyTime();
            int tgt = rs.getInt("target_time");
            int rt = rs.getInt("real_time");
            String goal = rs.getString("goal");

            st.setGoal(goal);
            st.setTargetTime(tgt);
            st.setRealTime(rt);

            list.add(st);

        }

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(list.get(0).getGoal(), list.get(0).getTargetTime()),
                        new PieChart.Data(list.get(1).getGoal(), list.get(1).getTargetTime()),
                        new PieChart.Data(list.get(2).getGoal(), list.get(2).getTargetTime()),
                        new PieChart.Data(list.get(3).getGoal(), list.get(3).getTargetTime()),
                        new PieChart.Data(list.get(4).getGoal(), list.get(4).getTargetTime()));
        pieChart.setData(pieChartData);

        pstmt.close();
        conn.close();

    }

    public void initReal() throws Exception {
        java.sql.Connection conn = JdbcUtil.getConnection();

        String sql = "select * from studytime where uid = 1";

        PreparedStatement pstmt;

        pstmt = conn.prepareStatement(sql);


        ResultSet rs = pstmt.executeQuery();
        ArrayList<StudyTime> list = new ArrayList<>();

        while (rs.next()) {
            StudyTime st = new StudyTime();
            int tgt = rs.getInt("target_time");
            int rt = rs.getInt("real_time");
            String goal = rs.getString("goal");

            st.setGoal(goal);
            st.setTargetTime(tgt);
            st.setRealTime(rt);

            list.add(st);
        }

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(list.get(0).getGoal(), list.get(0).getRealTime()),
                        new PieChart.Data(list.get(1).getGoal(), list.get(1).getRealTime()),
                        new PieChart.Data(list.get(2).getGoal(), list.get(2).getRealTime()),
                        new PieChart.Data(list.get(3).getGoal(), list.get(3).getRealTime()),
                        new PieChart.Data(list.get(4).getGoal(), list.get(4).getRealTime()));
        pieChart.setData(pieChartData);

        pstmt.close();
        conn.close();

    }

    @FXML
    LineChart<Integer, Integer> chart1;
    @FXML
    LineChart<String, Integer> chart2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            java.sql.Connection conn = JdbcUtil.getConnection();

            String sql = "select * from studytime where uid = 1";

            PreparedStatement pstmt;

            pstmt = conn.prepareStatement(sql);


            ResultSet rs = pstmt.executeQuery();
            ArrayList<StudyTime> list = new ArrayList<>();

            while (rs.next()) {
                StudyTime st = new StudyTime();
                int tgt = rs.getInt("target_time");
                int rt = rs.getInt("real_time");
                String goal = rs.getString("goal");

                st.setGoal(goal);
                st.setTargetTime(tgt);
                st.setRealTime(rt);

                list.add(st);
            }

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(list.get(0).getGoal(), list.get(0).getRealTime()),
                            new PieChart.Data(list.get(1).getGoal(), list.get(1).getRealTime()),
                            new PieChart.Data(list.get(2).getGoal(), list.get(2).getRealTime()),
                            new PieChart.Data(list.get(3).getGoal(), list.get(3).getRealTime()),
                            new PieChart.Data(list.get(4).getGoal(), list.get(4).getRealTime()));
            pieChart.setData(pieChartData);

            pstmt.close();
            conn.close();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        XYChart.Series<Integer, Integer> series1 = new XYChart.Series<>();
        series1.setName("Real Focus Time");
        series1.getData().add(new XYChart.Data<>(1, 300));
        series1.getData().add(new XYChart.Data<>(2, 420));
        series1.getData().add(new XYChart.Data<>(3, 280));
        series1.getData().add(new XYChart.Data<>(4, 200));
        series1.getData().add(new XYChart.Data<>(5, 410));
        series1.getData().add(new XYChart.Data<>(6, 370));
        series1.getData().add(new XYChart.Data<>(7, 355));
        chart1.getData().add(series1);

        XYChart.Series<Integer, Integer> series2 = new XYChart.Series<>();
        series2.setName("Target Focus Time");
        series2.getData().add(new XYChart.Data<>(1, 420));
        series2.getData().add(new XYChart.Data<>(2, 550));
        series2.getData().add(new XYChart.Data<>(3, 400));
        series2.getData().add(new XYChart.Data<>(4, 350));
        series2.getData().add(new XYChart.Data<>(5, 500));
        series2.getData().add(new XYChart.Data<>(6, 420));
        series2.getData().add(new XYChart.Data<>(7, 480));
        chart1.getData().add(series2);


    }

    @FXML
    Label Achievement;
    @FXML
    ImageView imgview;


    public void clickShow() throws Exception {
        java.sql.Connection conn = JdbcUtil.getConnection();

        String sql = "select * from studytime where uid = 1";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        int sumReal = 0;
        int sumTarget = 0;

        while (rs.next()) {

            sumTarget = sumTarget + rs.getInt("target_time");
            sumReal = sumReal + rs.getInt("real_time");

        }

        double a = (double) sumReal / (double) sumTarget;
        System.out.println(a);
        if (a == 1) {
            Achievement.setText("Congratulations! You accomplished your goal perfectly!!!");
            Image img = new Image(DiagramApplication.class.getResourceAsStream("images/diagram/perfect.gif"));
            imgview.setImage(img);
        }
        if (a >= 0.8 && a < 1) {
            Achievement.setText("You are awesome!You accompished more than 80% of your goal");
            Image img = new Image(DiagramApplication.class.getResourceAsStream("images/diagram/good.gif"));
            imgview.setImage(img);
        }
        if (a < 0.8) {
            Achievement.setText("Sorry, you didn't accomplish your goal very well");
            Image img = new Image(DiagramApplication.class.getResourceAsStream("images/diagram/bad.gif"));
            imgview.setImage(img);
        }

    }


}
