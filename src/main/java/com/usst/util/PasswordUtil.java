package com.usst.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {
    
    /**
     * 使用SHA256加密密码
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String encrypt(String password) {
        return DigestUtils.sha256Hex(password);
    }
    
    /**
     * 验证明文密码与加密密码是否匹配
     * @param plainPassword 明文密码
     * @param encryptedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean verify(String plainPassword, String encryptedPassword) {
        return encrypt(plainPassword).equals(encryptedPassword);
    }
}