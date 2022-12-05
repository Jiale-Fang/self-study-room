package com.example.demo.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {
    public static java.sql.Date localTimeToDate(LocalDateTime lt) {
        return new java.sql.Date(lt.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli());
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp){
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
