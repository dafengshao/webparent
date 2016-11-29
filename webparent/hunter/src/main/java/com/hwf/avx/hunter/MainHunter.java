package com.hwf.avx.hunter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hwf.avx.elsearch.ElsClient;
import com.hwf.avx.elsearch.ElsQueryClient;
import com.hwf.avx.elsearch.ScrollSearch;
import com.hwf.common.io.FileUtil;
import com.hwf.common.pool.SafeStack;

public class MainHunter {
	private static Logger logger = LoggerFactory.getLogger(MainHunter.class);
	//private static final String queryJSON = "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"leibie\":\"肛交\"}}]}}}";
	private static final String queryJSON = "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"actorList.name\":\"水原さな\"}}]}}}";
	private SafeStack<JSONObject> safeStack = new SafeStack<>();
	
	public void produce() throws Exception {
		ScrollSearch scrollSearch = new ScrollSearch();
		scrollSearch.setQueryJSON(queryJSON);
		ElsQueryClient els = new ElsQueryClient("127.0.0.1", 9300);
		SearchResponse response = null;
		SearchHits hits = null;
		do{
			
			response = els.query(scrollSearch);
			hits = response.getHits();
			if(hits==null){
				logger.info("{},查询结果为空",queryJSON);
				break;
			}
			if(hits.hits().length==0){
				break;
			}
			for(SearchHit hit:hits){
				Map<String, Object> source = hit.getSource();
				JSONObject json = buildMovieInfo(source);
				safeStack.push(json);
			}
			if(hits.hits().length<scrollSearch.getSize()){
				break;
			}
			scrollSearch.setSrollId(response.getScrollId());
			scrollSearch.setPageNo(scrollSearch.getPageNo()+1);
		}while(true);
		els.close();
	}
	private static JSONObject buildMovieInfo(Map<String, Object> source) {
		JSONObject json = new JSONObject();
		json.put("code", source.get("code"));
		json.put("faxingshijian", source.get("faxingshijian")==null?"0000-00-00":source.get("faxingshijian"));
		json.put("imgURLList", source.get("imgURLList"));
		return json;
	}
	public SafeStack<JSONObject> getSafeStack() {
		return safeStack;
	}
	
	
	
	
	
	public void consume(){
		JSONObject json = null;
		while((json=safeStack.pop(10000))!=null){
			String code = json.getString("code");
			//String newCodeDir = code.substring(0,code.indexOf("-"))+code.substring(code.indexOf("-"),code.indexOf("-")+2);
			String faxingshijian = json.getString("faxingshijian");
			StringBuilder sb = FileNameUtil.getOldDirPath(faxingshijian,code).append("ok");
			StringBuilder newsb = FileNameUtil.getDirPath(faxingshijian);
			File file = new File(sb.toString());
			if(file.exists()){
				FileUtil.copyDir(file.getParentFile(), newsb.toString(), false);
				File oldOKfile = new File(newsb.append(code).append("/").append("ok").toString());
				File newOKfile = new File(newsb.toString().replace("ok", code+".over"));
				oldOKfile.renameTo(newOKfile);
				logger.info("{},处理OK",newsb);
			}else{
				List<?> imgURLList = (ArrayList<?>) json.get("imgURLList");
				if(imgURLList==null){
					return ;
				}
			}
			/*for (int j = 0; j < imgURLList.size(); j++) {
				String imgUrl = (String) imgURLList.get(j);
				down(imgUrl,code);
			}*/
		}
	}
	
	public static void main(String[] args) throws Exception {
		final MainHunter hunter = new MainHunter();
		hunter.produce();
		hunter.consume();
		/*Thread thread1 = new Thread(){
			public void run() {
				try {
					hunter.produce();
				} catch (Exception e) {
					logger.error("produce error",e);
				}
			};
		};
		thread1.start();
		int i = 0;
		while(i++<5){
			Thread thread2 = new Thread(){
				public void run() {
					try {
						hunter.consume();
					} catch (Exception e) {
						logger.error("produce error",e);
					}
				};
			};
			thread2.start();
		}*/
	}
	
	/*
	private void down(String imgUrl, String code) {
		if(imgUrl.indexOf("pl.")>0){
			download(imgUrl, path);
		}else{
			download(imgUrl, path+"/s/");
			int lastIndexOf = imgUrl.lastIndexOf("-");
			if (lastIndexOf > 10) {
				String houzui = imgUrl.substring(lastIndexOf,
						imgUrl.length());
				String qian = imgUrl.substring(0,
						imgUrl.lastIndexOf("-"));
				String bigImgUrl = qian + "jp" + houzui;
				download(bigImgUrl, path);
			}
		}
		
	}*/
	
	
}
