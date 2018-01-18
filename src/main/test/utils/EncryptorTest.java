/**
 * @fileName :     EncryptorTest
 * @author :       zeeker
 * @date :         18/01/2018 14:20
 * @description :
 */

package utils;

import com.zeeker.utils.encryption.Encryptor;
import org.junit.Test;

public class EncryptorTest {

    @Test
    public void md5Base64Test(){
        String param = "123456";
        System.out.println(Encryptor.md5Base64Encrypt(param));
        System.out.println(Encryptor.base64Md5HexEncrypt(param));
    }

}
