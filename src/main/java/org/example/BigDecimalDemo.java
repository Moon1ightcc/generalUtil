package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * @author: limao
 * @date 2025-02-20
 * @description:
 */
public class BigDecimalDemo {

    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.minusMinutes(1));

        StringJoiner joiner = new StringJoiner(",");
    }

}
