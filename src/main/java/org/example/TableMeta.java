package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: limao
 * @date 2025-04-25
 * @description:
 */
class TableMeta {
    // Key = 表ID, Value = 列名列表
    private static final Map<Long, List<String>> columnMap = new HashMap<>();

    public static void addTableMeta(long tableId, List<String> columns) {
        columnMap.put(tableId, columns);
    }

    public static List<String> getColumns(long tableId) {
        return columnMap.get(tableId);
    }
}