package com.hwf.common.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	
	public static boolean copy(String inputFilePath, String outputFilePath) {
		File inFile = new File(inputFilePath);
		File outFile = new File(outputFilePath);
		return copy(inFile,outFile);
	}
	
	public static boolean copyDir(File inDirFile,String outDir,boolean delete){
		if(!inDirFile.exists()){
			return false;
		}
		if(!inDirFile.isDirectory()){
			return false;
		}
		String outFileDir = "";
		if(outDir.endsWith("/")){
			outFileDir = outDir+inDirFile.getName()+"/";
		}else{
			outFileDir = outDir+"/"+inDirFile.getName()+"/";
		}
		File[] listFiles = inDirFile.listFiles();
		if(listFiles!=null){
			for (int i = 0; i < listFiles.length; i++) {
				File inFile = listFiles[i];
				if(inFile.isFile()){
					File outFile = new File(outFileDir+inFile.getName());
					if(copy(inFile, outFile)&&delete){
						inFile.delete();
					}
				}else{
					copyDir(inFile,outFileDir,delete);
				}
			}
			if(delete)inDirFile.delete();
		}else if(delete){
			inDirFile.delete();
		}
		return true;
	}
	
	
	public static boolean copy(File inFile,File outFile){
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			
			if(!outFile.exists()){
				if(!outFile.getParentFile().exists()&&!outFile.getParentFile().mkdirs()&&!outFile.createNewFile()){
					logger.error("copy createNewFile fail");
					return false;
				}
			}
			fis = new FileInputStream(inFile);
			fos = new FileOutputStream(outFile);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			byte[] b = new byte[1024*2];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			return true;
		} catch (Exception e) {
			logger.error("copy exception",e);
			return false;
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				logger.warn("copy close ioexception",e);
			}

		}
	}
	
	public static boolean delete(File file){
		try {
			if(!file.exists()){
				return true;
			}
			Files.delete(file.toPath());
			return true;
		} catch (IOException e) {
			logger.warn("delete ioexception",e);
		}
		return false;
	}
	
	public static boolean delete(String filePath){
		File file = new File(filePath);
		return delete(file);
	}
	
	public static void main(String[] args) {
		File file = new File("F:/hunter/avmo.pw/movie/2016/01-01/EKDV-436/ok");
		File file1 = new File("F:/hunter/avmo.pw/movie/2016/01-01/EKDV-436/voer.ok");
		file.renameTo(file1);
	}
}
