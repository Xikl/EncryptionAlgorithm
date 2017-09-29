package com.ximo;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Security;
import java.util.Arrays;

/**
 * DES算法
 * Created by 朱文赵
 * 2017/9/29
 */
public class DESTest {

    private static String src = "朱文赵";

    public static void main(String[] args) {
        jdkDES();
        bcDES();
    }

    private static void jdkDES(){
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            //采用默认值 56位
            keyGenerator.init(56);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            //key的转换
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            //转化之后的key
            SecretKey convertSecretKey = factory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println(Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            //输出解密结果
            System.out.println(new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bcDES(){
        Security.addProvider(new BouncyCastleProvider());
        jdkDES();
    }

}
