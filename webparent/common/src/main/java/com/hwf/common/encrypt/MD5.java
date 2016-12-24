package com.hwf.common.encrypt;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5 {


   

   public final static String encode(String pwd,String charset) {  
       //用于加密的字符  
       char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
               'A', 'B', 'C', 'D', 'E', 'F' };  
       try {  
           //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中  
    	   byte[] btInput = null;
    	   if(charset!=null){
    		   btInput = pwd.getBytes(Charset.forName(charset));  
    	   }else{
    		   btInput = pwd.getBytes();  
    	   }
           //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。  
           MessageDigest mdInst = MessageDigest.getInstance("MD5");  
              
           //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要  
           mdInst.update(btInput);  
              
           // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文  
           byte[] md = mdInst.digest();  
              
           // 把密文转换成十六进制的字符串形式  
           int j = md.length;  
           char str[] = new char[j * 2];  
           int k = 0;  
           for (int i = 0; i < j; i++) {   //  i = 0  
               byte byte0 = md[i];  //95  
               str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5   
               str[k++] = md5String[byte0 & 0xf];   //   F  
           }  
              
           //返回经过加密后的字符串  
           return new String(str);  
              
       } catch (Exception e) {  
           return null;  
       }  
   }  
    public static void main(String[] args) {
    	String source = "何文锋";
		String md51 =MD5.encode(source,true);
		String md52 =MD5.encode(source);
		String md53 =MD5.encode(source);
		System.out.println(md51);
		System.out.println(md52);
		System.out.println(md53);
		
	}
	public static String encode(String source) {
		return encode(source,null);
	}
	
	public static String encode(String source,boolean toLower) {
		String e = encode(source,null);
		return e.toLowerCase();
	}
}
