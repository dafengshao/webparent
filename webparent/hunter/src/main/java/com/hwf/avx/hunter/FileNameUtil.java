package com.hwf.avx.hunter;

public final class FileNameUtil {
	
	private static String oldPath = "F:/hunter/avmo.pw/movie/";
	private static String newPath = "F:/hunter/new.avmo.pw.movie/";
	
	private static String defaultFaxingshijian = "0000-00-00";
	
	
	/**11-28之前的路径 yyyy-MM-dd*/
	public static StringBuilder getOldDirPath(String faxingshijian,String code){
		if(faxingshijian==null){
			faxingshijian = defaultFaxingshijian;
		}
		StringBuilder sb = new StringBuilder(oldPath);
		sb.append(faxingshijian.replaceFirst("-", "/")).append("/").append(code).append("/");
		return sb;
	}
	
	
	/**yyyy-MM-dd*/
	public static StringBuilder getDirPath(String faxingshijian){
		if(faxingshijian==null){
			faxingshijian = defaultFaxingshijian;
		}
		StringBuilder sb = new StringBuilder(newPath);
		sb.append(faxingshijian.replaceAll("-", "/")).append("/");
		return sb;
	}
	
	public static void main(String[] args) {
		System.out.println(getOldDirPath("0000-00-00","11"));
		System.out.println(getDirPath("0000-00-00"));
	}
}
