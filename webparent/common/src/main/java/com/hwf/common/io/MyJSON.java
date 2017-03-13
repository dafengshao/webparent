package com.hwf.common.io;

import java.util.ArrayList;

public class MyJSON {
	/** FastJSON
	QuoteFieldNames———-输出key时是否使用双引号,默认为true 
	WriteMapNullValue——–是否输出值为null的字段,默认为false 
	WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null 
	WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null 
	WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null 
	WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
	**/
	
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		System.out.println(list);
		try{
			for(String lt :list){
				list.remove(lt);// exception
			}
		}catch(Exception e){
			
		}
		
		
		/*for(int i = 0;i<list.size();i++){
			list.remove(i);//ok
		}*/
		System.out.println(list);
	}
}
