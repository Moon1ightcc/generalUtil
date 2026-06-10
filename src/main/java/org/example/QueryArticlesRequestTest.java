package org.example;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryArticlesRequestTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 请求接口
        request("/fm-open/api/queryArticles");
    }

    public static void request(String path) throws IOException, InterruptedException {
        String AppKey = "D4383CD7461544709CFA1ED72C52EEA6";
        String SecretKey = "18F1434ED7BA4F9DB0E298923B10B538";

        Map<String, String> map = Maps.newHashMapWithExpectedSize(3);
        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        map.put("timestamp", String.valueOf(timestamp));
        map.put("path", path);
        map.put("version", "1.0.0");
        List<String> storedKeys =
                Arrays.stream(map.keySet().toArray(new String[] {}))
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList());
        String sign =
                DigestUtils.md5DigestAsHex(
                                storedKeys.stream()
                                        .map(element -> String.join("", element, map.get(element)))
                                        .collect(Collectors.joining())
                                        .trim()
                                        .concat(SecretKey)
                                        .getBytes())
                        .toUpperCase();

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("startTime", "2026-06-09 17:52:31");
        requestBody.addProperty("endTime", "2026-06-09 17:52:33");
        requestBody.addProperty("pageSize", 10);
        requestBody.addProperty("pageNum", 1);

        System.out.println("AppKey:" + AppKey);
        System.out.println("sign:" + sign);
        System.out.println("timestamp:" + String.valueOf(timestamp));

        String gatewayURL = "http://cgi-sit.deepq.tech:9196";
//        String gatewayURL = "https://cgi.deepq.tech:9196";

        HttpRequest request =
                HttpRequest.newBuilder()
                        //                        .uri(URI.create("http://cgi-sit.deepq.tech:9196" + path))
                        .uri(URI.create(gatewayURL + path))
                        .header("appKey", AppKey)
                        .header("sign", sign)
                        .header("timestamp", String.valueOf(timestamp))
                        .header("Content-Type", "application/json")
                        .method("POST", HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                        .build();
        HttpResponse<String> response =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
