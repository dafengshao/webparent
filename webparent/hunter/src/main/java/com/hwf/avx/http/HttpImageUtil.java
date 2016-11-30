package com.hwf.avx.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpImageUtil {

	static final Logger logger = LoggerFactory.getLogger(HttpImageUtil.class);
	public static boolean down(String imgUrl,String filePath) throws Exception {
		logger.info("down ...{}",imgUrl);
		Response resultImageResponse = getResponse(imgUrl);
		if(resultImageResponse.statusCode()==200){
			File file = new java.io.File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream out = (new FileOutputStream(file));
			logger.info("down,{} ok.save to {}",imgUrl,file.getAbsolutePath());
			try{
				out.write(resultImageResponse.bodyAsBytes());           
			}finally{
				out.close();
			}
			return true;
		}else{
			return false;
		}
	}

	private static Response getResponse(String imgUrl) throws IOException {
		Response response = null;
		int trycount = 30;
		int i = 0;
		while(response == null){
			try{
				if(i>2){
					logger.info("Jsoup execute retrying :{}",i);
				}
				if(i<=trycount){
					Thread.sleep(new Random().nextInt(1600));
					if (i > 1) {
						Thread.sleep((i - 1) * 6 * 1000);
					}
				}
				response = Jsoup.connect(imgUrl).timeout(10000).ignoreContentType(true)
						.header("User-Agent", "Mozilla//5.0 (Windows NT 6.1; Win64; x64) AppleWebKit//537.36 (KHTML, like Gecko) Chrome//53.0.2785.116 Safari//537.36")
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.header("Referer", "https://avmo.pw/cn/")
						//.proxy("127.0.0.1", 8787)
						.execute();
			}catch(Exception e){
				i++;
				if(i>=3){
					logger.error("getResponse error",e);
				}
			}
		}
		return response;
	}
	
	public static boolean downImage(String imgUrl,String dirPath,int retrycount){
		String filePath = dirPath;
		String fileName = "";
		if(dirPath.endsWith("/")||dirPath.indexOf(".")>0){
			fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1,imgUrl.length());
			if(!dirPath.endsWith("/")){
				filePath = filePath+"/"+fileName;
			}else{
				filePath += fileName;
			}
		}
		
		int i = 0;
		while(i<retrycount){
			try{
				if(down(imgUrl, filePath)){
					return true;
				}
			}catch(Exception e){
				i++;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String path = "F:/hunter/test/";
		String imgUrl = "https://jp.netcdn.space/digital/video/mvsd00307/mvsd00307pl.jpg";
		downImage(imgUrl, path, 1);
	}
}
