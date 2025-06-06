package org.example;

/**
 * @author: limao
 * @date 2025-02-21
 * @description:
 */
public class StringUtils {

    /**
     * 计算两个字符串的相似度，返回0-1之间的数值，1表示完全相似
     *
     * @param str1 文本1
     * @param str2 文本2
     * @return 相似度
     */
    public static double stringSimilarity(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }

        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) {
            return 1.0; // 两个空字符串视为完全相似
        }

        int editDistance = computeLevenshteinDistance(str1, str2);
        return 1.0 - (double) editDistance / maxLength;
    }

    private static int computeLevenshteinDistance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 1,
                            Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        String str1 = "天然气供需短期趋紧，全球气价短暂回升。美因供给回落、需求提升，欧洲受气温及进口政策影响。我国LNG进口成本下降，油价下跌利好后续降本，利好下游城燃。 ";
        String str2 = "天然气供需短期趋紧，全球气价短暂回升，美、欧、东北亚等地气价均有涨幅。受油价下跌影响，我国LNG进口成本下降，预计2H25进口价中枢或进一步下移，利好城燃降本。 ";
        System.out.println(stringSimilarity(str1, str2));
    }


}
