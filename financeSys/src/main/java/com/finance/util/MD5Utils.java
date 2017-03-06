package com.finance.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String generate(String text){
		String result = null;
		try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(text.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            result = buf.toString();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } 
		return result;  
	}
	
	public static void main(String[] args) {
		System.out.println(generate("123456"));
	}
	
}
