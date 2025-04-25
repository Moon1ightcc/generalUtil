package org.example;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.shyiko.mysql.binlog.BinaryLogFileReader;

import java.io.*;

import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.ChecksumType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BinlogToTextExporter {

    // 默认字符集配置（根据实际情况调整，例如：GBK, ISO-8859-1 等）
    private static final String DEFAULT_CHARSET = "GBK";
    // 存储表结构元数据，key为tableId，value为字段类型数组
    private static final Map<Long, int[]> tableColumnTypes = new HashMap<>();

    public static void main(String[] args) {
        String binlogPath = "C:\\Users\\shenqing\\OneDrive\\Desktop\\mysql-bin.000373"; // 替换为你的 binlog 文件路径
        String outputPath = "C:\\Users\\shenqing\\OneDrive\\Desktop\\binlog_output.txt";                // 输出文件路径

        try (
                BinaryLogFileReader reader = createBinlogReader(binlogPath);
                PrintWriter writer = new PrintWriter(new FileWriter(outputPath))
        ) {
            writer.println("=== Binlog Events ===");
            for (Event event; (event = reader.readEvent()) != null; ) {
                String eventLog = formatEvent(event);
                writer.println(eventLog);
            }
            System.out.println("Binlog 解析完成，结果已保存至: " + outputPath);
        } catch (Exception e) {
            System.err.println("解析失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static BinaryLogFileReader createBinlogReader(String binlogPath) throws IOException {
        File binlogFile = new File(binlogPath);
        EventDeserializer deserializer = new EventDeserializer();
        deserializer.setChecksumType(ChecksumType.NONE); // 禁用校验和验证
        return new BinaryLogFileReader(binlogFile, deserializer);
    }

    private static String formatEvent(Event event) {
        EventHeader header = event.getHeader();
        StringBuilder sb = new StringBuilder();
        String format = DateUtil.format(new DateTime(header.getTimestamp()), "yyyy-MM-dd HH:mm:ss");

        // 基础事件信息
        sb.append(String.format("\n[Event] %s | Timestamp: %s | server_id: %d",
                header.getEventType(),
                format,
                header.getServerId()
        ));

        // 详细事件内容
        switch (header.getEventType()) {
            case TABLE_MAP:
                TableMapEventData tableData = event.getData();
                sb.append(String.format("\n  Database: %s | Table: %s (ID=%d)",
                        tableData.getDatabase(),
                        tableData.getTable(),
                        tableData.getTableId()
                ));
                break;

            case WRITE_ROWS:
                WriteRowsEventData insertData = event.getData();
                sb.append("\n  Insert Rows:");
                for (Object[] row : insertData.getRows()) {
                    sb.append("\n    ").append(formatRow(row));
                }
                break;

            case UPDATE_ROWS:
                UpdateRowsEventData updateData = event.getData();
                sb.append("\n  Update Rows:");
                for (Map.Entry<Serializable[], Serializable[]> entry : updateData.getRows()) {
                    sb.append("\n    Before: ").append(formatRow(entry.getKey()));
                    sb.append("\n    After : ").append(formatRow(entry.getValue()));
                }
                break;

            case DELETE_ROWS:
                DeleteRowsEventData deleteData = event.getData();
                sb.append("\n  Delete Rows:");
                for (Object[] row : deleteData.getRows()) {
                    sb.append("\n    ").append(formatRow(row));
                }
                break;

            case QUERY:
                QueryEventData queryData = event.getData();
                sb.append(String.format("\n  SQL: %s | Database: %s",
                        queryData.getSql(),
                        queryData.getDatabase()
                ));
                break;

            default:
                sb.append("\n  [Raw Data] ").append(String.valueOf(event.getData()));
        }
        return sb.toString();
    }
    private static String formatRow(Object[] row) {
        StringBuilder sb = new StringBuilder();
        for (Object value : row) {
            if (value instanceof byte[]) {
                // 将字节数组转为字符串，并处理转义符
                String strValue = new String((byte[]) value, StandardCharsets.UTF_8);
                strValue = unescapeJson(strValue); // 自定义转义符处理
                sb.append(strValue).append(", ");
            } else {
                sb.append(value).append(", ");
            }
        }
        return !sb.isEmpty() ? sb.substring(0, sb.length() - 2) : "";
    }

    private static String unescapeJson(String json) {
        // 处理常见的 JSON 转义符
        return json.replace("\\\"", "\"")  // 转义双引号
                .replace("\\n", "\n")   // 转义换行符
                .replace("\\t", "\t")    // 转义制表符
                .replace("\\\\", "\\");  // 转义反斜杠
    }
}