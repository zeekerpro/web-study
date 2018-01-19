/**
 * @fileName :     Encryptor
 * @author :       zeeker
 * @date :         18/01/2018 11:43
 * @description :
 */

package com.zeeker.utils.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {

    /**
     * 先获取参数的 md5 指纹摘要，然后再 base64 编码 -> 24 个字符
     * @param param
     * @return
     */
    public static String md5Base64Encrypt(String param){
        String encryptStr = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] source = param.getBytes();
            byte[] temp = messageDigest.digest(source);
            encryptStr = Base64.getEncoder().encodeToString(temp);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptStr;
    }


    /**
     * 先将参数 base64 编码，然后获取指纹，再将16个字节的指纹转化16进制字符表示 -> 32 个字符
     * @param param
     * @return
     */
    public static String base64Md5HexEncrypt(String param){
        String encryStr = null;
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] base64Source = Base64.getEncoder().encode(param.getBytes());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] md5Source = messageDigest.digest(base64Source);
            char[] str = new char[md5Source.length * 2];
            for (int i = 0; i < md5Source.length; i++){
                str[i * 2] = hexDigits[md5Source[i] >>> 4 & 0xf];
                str[i * 2 + 1] = hexDigits[md5Source[i] & 0xf];
            }
            encryStr = new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryStr;
    }
}
