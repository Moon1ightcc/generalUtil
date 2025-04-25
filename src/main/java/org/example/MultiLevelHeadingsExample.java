package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.poi.xwpf.usermodel.ParagraphAlignment.RIGHT;

public class MultiLevelHeadingsExample {
    public static void main(String[] args) {
        XWPFDocument document = new XWPFDocument();

        try {
            // 创建标题1
            XWPFParagraph title1 = document.createParagraph();
            title1.setAlignment(RIGHT);
            XWPFRun run1 = title1.createRun();
            run1.setText("这是标题1");
            run1.setFontSize(24);
            run1.setBold(true);
            run1.addBreak(); // 换行，为了格式上的清晰

            // 创建标题2
            XWPFParagraph title2 = document.createParagraph();
            title2.setStyle("Heading 2"); // 设置样式为Heading2
            XWPFRun run2 = title2.createRun();
            run2.setText("这是标题2（属于标题1的子标题）");
            run2.addBreak();

            // 创建标题3
            XWPFParagraph title3 = document.createParagraph();
            title3.setStyle("Heading3"); // 设置样式为Heading3
            XWPFRun run3 = title3.createRun();
            run3.setText("这是标题3（属于标题2的子标题）");
            run3.addBreak();

            // 创建正文段落
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle("BodyText"); // 设置样式为正文
            XWPFRun run = paragraph.createRun();
            run.setText("这是一些正文内容。");

            // 将文档写入文件
            try (FileOutputStream out = new FileOutputStream("MultiLevelHeadingsExample.docx")) {
                document.write(out);
            }

            System.out.println("Word文档创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}