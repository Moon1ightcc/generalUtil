package org.example;

/**
 * @author: limao
 * @date 2025-05-21
 * @description:
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstStockCompanyMatcher {

    public static void main(String[] args) {
        String text = "野村东方国际证券，今日发布公告，广发证券股价上涨。";
        String regex = "^[^，,]*?([\\u4e00-\\u9fa5]{2,6}证券)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);



        if (matcher.find()) {
            System.out.println("第一个匹配的公司: " + matcher.group(1)); // 输出: 中信证券
        } else {
            System.out.println("未匹配到符合要求的公司名");
        }
    }
}
