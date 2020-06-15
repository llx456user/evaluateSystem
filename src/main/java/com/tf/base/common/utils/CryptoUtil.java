package com.tf.base.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * 加密 解密工具类
 *
 */
public class CryptoUtil {
	
	
	private static final String Algorithm = "DESede"; //定义加密算法,可用 DES,DESede,Blowfish  
    //keybyte为加密密钥，长度为24字节      
    //src为被加密的数据缓冲区（源）  
    public static byte[] encryptMode(byte[] keybyte,byte[] src){  
         try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //加密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.ENCRYPT_MODE, deskey);  
            return c1.doFinal(src);//在单一方面的加密或解密  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            // TODO: handle exception  
             e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(java.lang.Exception e3){  
            e3.printStackTrace();  
        }  
        return null;  
    }  
      
    //keybyte为加密密钥，长度为24字节      
    //src为加密后的缓冲区  
    public static byte[] decryptMode(byte[] keybyte,byte[] src){  
        try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //解密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            // TODO: handle exception  
            e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(java.lang.Exception e3){  
            e3.printStackTrace();  
        }  
        return null;          
    }  
      
    //转换成十六进制字符串  
    public static String byte2Hex(byte[] b){  
        String hs="";  
        String stmp="";  
        for(int n=0; n<b.length; n++){  
            stmp = (java.lang.Integer.toHexString(b[n]& 0XFF));  
            if(stmp.length()==1){  
                hs = hs + "0" + stmp;                 
            }else{  
                hs = hs + stmp;  
            }  
            if(n<b.length-1)hs=hs+":";  
        }  
        return hs.toUpperCase();          
    }  
    public static byte[] hex(String username){  
        String key = "Interface";//关键字  
        String f = DigestUtils.md5Hex(username+key);  
        byte[] bkeys = new String(f).getBytes();  
        byte[] enk = new byte[24];  
        for (int i=0;i<24;i++){  
            enk[i] = bkeys[i];  
        }  
        return enk;  
    }  
    
    /**
     * 获取加密后的数据
     * @param username  传入的用户名
     * @param password  传入的原始密码
     * @return
     */
    public static String getEncryptData(String username,String password){
    	
    	try{
    		
	    	byte[] enk = hex(username);
	    	byte[] encoded = encryptMode(enk,password.getBytes());  
	    	return  Base64.encodeBase64String(encoded);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    /**
     * 获取解密后的数据
     * @param username  传入的用户名
     * @param encryppwd 加密后的密码
     * @return
     */
    public static String getDecryptData(String username,String encryppwd){
    	
    	try{
    		
    	    byte[] enk = hex(username);//用户名  
    	    byte[] reqPassword = Base64.decodeBase64(encryppwd);  
            byte[] srcBytes = decryptMode(enk,reqPassword);
            return new String(srcBytes);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null ;
    	}
    }
    
    
    public static void main(String[] args) {  
    	
    	String encryptData =   getEncryptData("admin","123456");
    	
    	System.out.println("加密后的字符串:" + encryptData);
    	String  decryptData =  getDecryptData("admin",encryptData);
    	
    	System.out.println("解密后的字符串:" + decryptData);
    	
    }  

}
