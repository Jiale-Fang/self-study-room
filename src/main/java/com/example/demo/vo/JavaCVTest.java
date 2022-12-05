package com.example.demo.vo;

import com.example.demo.chat.Server;
import com.example.demo.pojo.Task;
import com.example.demo.util.TimeUtil;
import org.bytedeco.javacv.*;
import javax.swing.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class JavaCVTest {

    public static void main(String[] args) throws InterruptedException, FrameGrabber.Exception {
////        new JavaCVTest().testCamera();
//        final LocalDateTime now = LocalDateTime.now();
////创建一个日期时间格式器
//        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
////LocalDateTime format实例方法用来将时间转为字符串
//        final String format = now.format(dateTimeFormatter);
//        System.out.println(format);
//        Date date = TimeUtil.localTimeToDate(LocalDateTime.now());
//        System.out.println(date);
        while (true){
            System.out.println();
        }
    }

    public void testCamera() throws InterruptedException, FrameGrabber.Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("Camera");//新建一个窗口
        canvas.setSize(600, 400);
        canvas.setCanvasSize(600, 400);
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        while (true) {
            if (!canvas.isDisplayable()) {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(-1);//退出
            }
            Frame frame = grabber.grab();
            canvas.showImage(frame);//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
            Thread.sleep(10);//50毫秒刷新一次图像
        }
    }

}