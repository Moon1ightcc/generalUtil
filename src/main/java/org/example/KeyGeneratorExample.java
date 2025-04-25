package org.example;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyGeneratorExample {
    public static void main(String[] args) throws Exception {
        // 创建 KeyGenerator 实例，指定算法为 AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");

        // 初始化密钥长度为 256 位
        keyGen.init(256);

        // 生成密钥
        SecretKey secretKey = keyGen.generateKey();

        // 将密钥转换为 Base64 编码的字符串
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Key: " + base64Key);
    }
}
