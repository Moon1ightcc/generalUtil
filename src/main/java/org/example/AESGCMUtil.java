package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AESGCMUtil {
    public static void main(String[] args) {
        try {
            // Base64 编码的密钥
            String base64Key = "yQ6dZED+bYJzqnL402Z4X6FnwCJCkrmHmMW7oYAMxTU=";

            // 明文数据
            String plainText = "{\n" +
                    "    \"question\": \"农业\",\n" +
                    "    \"app\": \"热点掘金\",\n" +
                    "    \"channel\": \"深擎\",\n" +
                    "    \"userId\": \"12345678\",\n" +
                    "    \"eventTime\": \"2025-03-18 14:20:00\",\n" +
                    "    \"eventType\": \"搜索\",\n" +
                    "    \"eventData\": \"半导体\"    \n" +
                    "}";
            System.out.println("Plain Text: " + plainText);

            // 加密
            String encryptedData = AESGCMUtil.encrypt(plainText, base64Key);
            System.out.println("Encrypted Data: " + encryptedData);

            // 解密
            String decryptedText = AESGCMUtil.decrypt(encryptedData, base64Key);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final int GCM_IV_LENGTH = 12; // IV 长度（12字节）
    private static final int GCM_TAG_LENGTH = 16; // 认证标签长度（16字节）
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    /**
     * 加密
     *
     * @param plaintext 明文数据
     * @param base64Key Base64 编码的密钥
     * @return 加密后的数据（Base64 编码）
     */
    public static String encrypt(String plaintext, String base64Key) throws Exception {
        // 解码 Base64 密钥
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // 生成随机 IV
        byte[] iv = generateIV();

        // 初始化加密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        // 加密数据
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        // 拼接 IV 和密文
        byte[] encryptedData = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, encryptedData, 0, iv.length);
        System.arraycopy(ciphertext, 0, encryptedData, iv.length, ciphertext.length);

        // 返回 Base64 编码的加密数据
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 解密
     *
     * @param encryptedData 加密后的数据（Base64 编码）
     * @param base64Key     Base64 编码的密钥
     * @return 解密后的明文数据
     */
    public static String decrypt(String encryptedData, String base64Key) throws Exception {
        // 解码 Base64 密钥
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // Base64 解码
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);

        // 提取 IV（前12字节）
        byte[] iv = Arrays.copyOfRange(decodedData, 0, GCM_IV_LENGTH);

        // 提取密文（剩余部分）
        byte[] ciphertext = Arrays.copyOfRange(decodedData, GCM_IV_LENGTH, decodedData.length);

        // 初始化解密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

        // 解密数据
        byte[] decryptedData = cipher.doFinal(ciphertext);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /**
     * 生成随机 IV
     */
    private static byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new java.security.SecureRandom().nextBytes(iv);
        return iv;
    }
}