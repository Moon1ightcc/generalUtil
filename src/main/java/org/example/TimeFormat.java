package org.example;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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


        long currentTimeMillis = System.currentTimeMillis();

        Long aLong = Long.valueOf(String.valueOf(currentTimeMillis));

        String format = DateUtil.format(new DateTime(aLong), "yyyy-MM-dd");
        System.out.println(format);

    }

}