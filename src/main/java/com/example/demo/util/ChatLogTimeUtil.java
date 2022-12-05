package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatLogTimeUtil {

    /**
     * format time
     *
     * @param ldt
     * @return
     */
    public static String formatTime(LocalDateTime ldt) {
        long time = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Date date = new Date();
        date.setTime(time);

        if (isSameYear(date)) { //Same year: MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
            if (isSameDay(date)) { //Same Day: HH:mm
                int minute = minutesAgo(time);
                if (minute < 60) {//In one hour
                    if (minute <= 1) {//In one minute
                        return "just now";
                    } else {
                        return minute + "minutes ago";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYesterday(date)) {//yesterday: HH:mm
                    return "Yesterday " + simpleDateFormat.format(date);
                } else if (isSameWeek(date)) {//this week: today's day + HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "Monday";
                    }
                    if (date.getDay() == 2) {
                        weekday = "Tuesday";
                    }
                    if (date.getDay() == 3) {
                        weekday = "Wednesday";
                    }
                    if (date.getDay() == 4) {
                        weekday = "Thursday";
                    }
                    if (date.getDay() == 5) {
                        weekday = "Friday";
                    }
                    if (date.getDay() == 6) {
                        weekday = "Saturday";
                    }
                    if (date.getDay() == 0) {
                        weekday = "Sunday";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {//Same year: MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.US);
                    return sdf.format(date);
                }
            }
        } else {//Not this year: yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            return sdf.format(date);
        }
    }

    /**
     * a few minutess ago
     *
     * @param time
     * @return
     */
    public static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }


    /**
     * is the same week?
     *
     * @param date
     * @return
     */
    public static boolean isSameWeek(Date date) {
        if (isSameYear(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int a = calendar.get(Calendar.DAY_OF_YEAR);

            Date now = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(now);
            int b = calendar1.get(Calendar.DAY_OF_WEEK);
            return a == b;
        } else {
            return false;
        }
    }

    /**
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        Date yesterday = getNextDay(date, 1);
        return isSameDay(yesterday);
    }

    /**
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        return isEquals(date, "yyyy-MM-dd");
    }

    /**
     *
     * @param date
     * @return
     */
    public static boolean isSameMonth(Date date) {
        return isEquals(date, "yyyy-MM");
    }

    /**
     * @param date
     * @return
     */
    public static boolean isSameYear(Date date) {
        return isEquals(date, "yyyy");
    }


    /**
     *
     * @param date
     * @return flag
     */
    private static boolean isEquals(Date date, String format) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        date = calendar.getTime();
        return date;
    }
}
