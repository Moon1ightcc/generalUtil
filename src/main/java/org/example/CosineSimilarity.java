package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: limao
 * @date 2025-05-14
 * @description: 余弦相似度计算
 */

public class CosineSimilarity {

    public static BigDecimal compute(String s1, String s2) {
        Map<Character, int[]> vector = new HashMap<>();

        // 统计字符频率（不转小写）
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            vector.computeIfAbsent(c, k -> new int[2])[0]++;
        }
        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            vector.computeIfAbsent(c, k -> new int[2])[1]++;
        }

        // 计算余弦相似度（逻辑同原始代码）
        double dotProduct = 0, norm1 = 0, norm2 = 0;
        for (Map.Entry<Character, int[]> entry : vector.entrySet()) {
            int[] counts = entry.getValue();
            dotProduct += counts[0] * counts[1];
            norm1 += Math.pow(counts[0], 2);
            norm2 += Math.pow(counts[1], 2);
        }
        double denominator = Math.sqrt(norm1) * Math.sqrt(norm2);
        return denominator == 0 ? BigDecimal.valueOf(0) : BigDecimal.valueOf(dotProduct / denominator).setScale(2, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        String s1 = "你好吗";
        String s2 = "你好";
        System.out.println("Similarity: " + compute(s1, s2));
    }
}