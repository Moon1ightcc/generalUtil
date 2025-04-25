package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableSchemaLoader {
    // 存储表结构：Key = "database.table", Value = 列名列表
    private final Map<String, List<String>> schemaMap = new HashMap<>();

    // 正则提取 CREATE TABLE 语句中的列名
    private static final Pattern CREATE_TABLE_PATTERN = Pattern.compile(
            "CREATE TABLE `(\\w+)`.`(\\w+)` \\((.+?)\\)", Pattern.DOTALL
    );
    private static final Pattern COLUMN_PATTERN = Pattern.compile(
            "`(\\w+)`\\s+\\w+.*?(,|$)", Pattern.DOTALL
    );

    public void loadFromFile(String filePath) throws IOException {
        String sql = new String(Files.readAllBytes(Paths.get(filePath)));
        Matcher tableMatcher = CREATE_TABLE_PATTERN.matcher(sql);
        while (tableMatcher.find()) {
            String database = tableMatcher.group(1);
            String table = tableMatcher.group(2);
            String columnsPart = tableMatcher.group(3).trim();

            List<String> columns = new ArrayList<>();
            Matcher colMatcher = COLUMN_PATTERN.matcher(columnsPart);
            while (colMatcher.find()) {
                columns.add(colMatcher.group(1));
            }
            String key = database + "." + table;
            schemaMap.put(key, columns);
        }
    }

    public List<String> getColumns(String database, String table) {
        return schemaMap.get(database + "." + table);
    }
}