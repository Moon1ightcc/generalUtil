package org.example;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


/**
 * @author: limao
 * @date 2025-02-20
 * @description:
 */
public class BigDecimalDemo {

    public static void main(String[] args) {

        LocalDate now = LocalDate.now();
        System.out.println(now);
        String format = LocalDateTimeUtil.format(now, "yyyy年MM月dd日");

        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println("本周一是" + LocalDateTimeUtil.format(monday, "yyyy年MM月dd日"));

        DateTime dateTime = DateUtil.beginOfWeek(DateUtil.date(), true);
        String formatted = DateUtil.format(dateTime, "yyyy年MM月dd日");
        System.out.println("DateTime格式化的日期 "+ formatted);

        System.out.println(MessageFormat.format("https://dq-front-deliver-sit.deepq.tech/front-end/admin-aigc-content-sit-h5/#/plate?code={0}&time={1}","123",LocalDate.now()));


        System.out.println(format);
    }

}
