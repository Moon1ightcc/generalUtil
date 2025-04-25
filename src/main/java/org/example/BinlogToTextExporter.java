package org.example;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;


import java.io.*;

import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.ChecksumType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BinlogToTextExporter {

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

        // 基础事件信息
        sb.append(String.format("\n[Event] %s | Timestamp: %d | server_id: %d",
                header.getEventType(),
                header.getTimestamp(),
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
                // 处理二进制数据（如 BLOB/VARCHAR）
                sb.append(new String((byte[]) value, StandardCharsets.UTF_8)).append(", ");
            } else {
                sb.append(value).append(", ");
            }
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
    }
}