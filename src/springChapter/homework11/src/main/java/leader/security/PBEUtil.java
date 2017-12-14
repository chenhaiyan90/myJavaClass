package leader.security;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/** 对称加密PBE算法
 */
public class PBEUtil {
   /** 
    * 支持以下任意一种算法 
    *  
    * <pre> 
    * PBEWithMD5AndDES  
    * PBEWithMD5AndTripleDES  
    * PBEWithSHA1AndDESede 
    * PBEWithSHA1AndRC2_40 
    * </pre> 
    */  
   public static final String ALGORITHM = "PBEWithSHA1AndRC2_40";  
 
   /** 
    * 盐初始化 
    *  
    * @return 
    * @throws Exception 
    */  
   private static byte[] initSalt() throws Exception {  
       byte[] salt = new byte[]{0b1,0x2,0x3,0x4,0x5,0x6,0x7,0x8};  
       return salt;  
   }  
 
   /** 
    * 转换密钥<br> 
    *  
    * @param password 
    * @return 
    * @throws Exception 
    */  
   private static Key keyGenerator(String password) throws Exception {  
       PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());  
       //创建一个密匙工厂，然后用它把PBEKeySpec转换成
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
       SecretKey secretKey = keyFactory.generateSecret(keySpec);  
       return secretKey;  
   }  
 
   /** 
    * 加密 
    *  
    * @param data 
    *            数据 
    * @param password 
    *            密码 
    * @param salt 
    *            盐 
    * @return 
    * @throws Exception 
    */  
   public static String encrypt(String data, String password ) throws Exception {  
       Key key = keyGenerator(password);  
       PBEParameterSpec paramSpec = new PBEParameterSpec(initSalt(), 100); 
       // 实例化Cipher对象，它用于完成实际的加密操作
       Cipher cipher = Cipher.getInstance(ALGORITHM); 
       // 初始化Cipher对象，设置为加密模式
       cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
       byte[] buff = cipher.doFinal(data.getBytes());
       System.out.println(Arrays.toString(buff));
    // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
       return java.util.Base64.getEncoder().encodeToString(buff);
       
       
   }  
 
   /** 
    * 解密 
    *  
    * @param data 
    *            数据 
    * @param password 
    *            密码 
    * @param salt 
    *            盐 
    * @return 
    * @throws Exception 
    */  
   public static String decrypt(String data, String password) throws Exception {  
       Key key = keyGenerator(password);  
       PBEParameterSpec paramSpec = new PBEParameterSpec(initSalt(), 100); 
       // 实例化Cipher对象，它用于完成实际的解密操作
       Cipher cipher = Cipher.getInstance(ALGORITHM);
       //初始化Cipher对象，设置为解密模式
       cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
       // 执行解密操作
       byte[] buff = cipher.doFinal( java.util.Base64.getDecoder().decode(data));
       System.out.println(Arrays.toString(buff));
       return new String(buff);
   }
   
   public static void main(String[] args) throws Exception {
	   String pwd=SecurityConfig.encodeStr;
	   String inputStr = "aqws123$wwwwwwwwwwwww";
       System.out.println("原文: " + inputStr);  
 
       System.out.println("密码: " + pwd);  
 
        
 
       String encryptData = encrypt(inputStr, pwd);  
       System.out.println("加密后: " + encryptData);  
 
       String decryptData = decrypt(encryptData, pwd);
       System.out.println("解密后: " + decryptData);  
   }
}