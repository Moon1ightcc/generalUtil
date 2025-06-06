package org.example;

import java.util.HashSet;
import java.util.Set;

public class SorensenDice {
    public static double similarity(String s1, String s2) {
        Set<String> bigrams1 = getBigrams(s1);
        Set<String> bigrams2 = getBigrams(s2);

        Set<String> intersection = new HashSet<>(bigrams1);
        intersection.retainAll(bigrams2);
        return (2.0 * intersection.size()) / (bigrams1.size() + bigrams2.size());
    }

    private static Set<String> getBigrams(String s) {
        Set<String> bigrams = new HashSet<>();
        for (int i = 0; i < s.length() - 1; i++) {
            bigrams.add(s.substring(i, i + 2));
        }
        return bigrams;
    }

    public static void main(String[] args) {
        String str1 = "天然气供需短期趋紧，全球气价短暂回升。美因供给回落、需求提升，欧洲受气温及进口政策影响。我国LNG进口成本下降，油价下跌利好后续降本，利好下游城燃。 ";
        String str2 = "天然气供需短期趋紧，全球气价短暂回升，美、欧、东北亚等地气价均有涨幅。受油价下跌影响，我国LNG进口成本下降，预计2H25进口价中枢或进一步下移，利好城燃降本。 ";
        System.out.println("Similarity: " + similarity(str1, str2)); // 0.25
    }
}
