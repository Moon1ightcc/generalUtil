package org.example;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TimeFormat {
    public static void main(String[] args) {
        String publishTime = "1743576021000";
        String format1 = DateUtil.format(new DateTime(Long.parseLong(publishTime)), "yyyy-MM-dd HH:mm:ss");
        System.out.println(format1);

        LocalDateTime now = LocalDateTimeUtil.now();

        String formatted = LocalDateTimeUtil.format(now, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String string = DateUtil.now();

        LocalDate parse = LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String format2 = LocalDateTimeUtil.format(parse, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format2);

        LocalTime localTime = LocalTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(localTime);

        LocalTime thresholdTime = LocalTime.of(12, 30, 10);
        System.out.println(thresholdTime);

        // 方式1：使用 DateUtil.parse 解析
        String formattedDate1 = DateUtil.format(DateTime.now(), "yyyy-MM-dd");
        System.out.println(formattedDate1);  // 输出: 2025-05-05


        long currentTimeMillis = System.currentTimeMillis();

        Long aLong = Long.valueOf(String.valueOf(currentTimeMillis));

        String format = DateUtil.format(new DateTime(aLong), "yyyy-MM-dd");


        LocalDate localDate = LocalDate.parse("2025-05-30 00:00:00", DatePattern.NORM_DATETIME_FORMATTER);
        System.out.println("localDate: " + localDate);

        String s = " 12134";
        System.out.println(s.trim());

        System.out.println(format);

    }

}