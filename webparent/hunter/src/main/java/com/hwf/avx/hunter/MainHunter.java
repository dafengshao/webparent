package com.hwf.avx.hunter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hwf.avx.config.PrefixConfig;
import com.hwf.avx.config.PrefixConfig.AvType;
import com.hwf.avx.elsearch.ElsClient;
import com.hwf.avx.elsearch.ElsQueryClient;
import com.hwf.avx.elsearch.ScrollSearch;
import com.hwf.avx.http.HttpImageUtil;
import com.hwf.common.io.FileUtil;
import com.hwf.common.pool.SafeStack;

public class MainHunter {
	private static Logger logger = LoggerFactory.getLogger(MainHunter.class);
	// private static final String queryJSON =
	// "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"leibie\":\"肛交\"}}]}}}";
	// private static final String queryJSON =
	// "{\"query\":{\"bool\":{\"must\":[{\"term\":{\"actorList.name\":\"佐々木あき\"}}]}}}";
	
	public static void main(String[] args) throws Exception {
		final MainHunter hunter = new MainHunter();
		int poolThead = 10;
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(poolThead);
		FileNameUtil.setNewPath("F:/hunter/佐々木あき/");
		FileNameUtil.setSavePathType(1);
		hunter.produce();
		for(int i=0;i<poolThead;i++){
			newFixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					hunter.consume();
				}
			});
		}
		
		
	}
	
	private static final String queryJSON = 
			"{\"query\":{\"bool\":"
			+ "{\"must\":[{\"term\":{\"leibie\":\"单体作品\"}},"
			+ "{\"term\":{\"actorList.name\":\"佐々木あき\"}}]}}}";
	
	private static final PrefixConfig config = new PrefixConfig(AvType.mo);
	
	private SafeStack<JSONObject> safeStack = new SafeStack<>();

	public void produce() throws Exception {
		ScrollSearch scrollSearch = new ScrollSearch();
		scrollSearch.setQueryJSON(queryJSON);
		ElsQueryClient els = new ElsQueryClient("127.0.0.1", 9300);
		SearchResponse response = null;
		SearchHits hits = null;
		do {
			response = els.query(scrollSearch);
			hits = response.getHits();
			if (hits == null) {
				logger.info("{},查询结果为空", queryJSON);
				break;
			}
			if (hits.hits().length == 0) {
				break;
			}
			for (SearchHit hit : hits) {
				Map<String, Object> source = hit.getSource();
				JSONObject json = buildMovieInfo(source);
				logger.info("movie:{}",json);
				safeStack.push(json);
			}
			if (hits.hits().length < scrollSearch.getSize()) {
				break;
			}
			scrollSearch.setSrollId(response.getScrollId());
			scrollSearch.setPageNo(scrollSearch.getPageNo() + 1);
		} while (true);
		els.close();
	}

	private static JSONObject buildMovieInfo(Map<String, Object> source) {
		JSONObject json = new JSONObject();
		json.put("code", source.get("code"));
		json.put(
				"faxingshijian",
				source.get("faxingshijian") == null ? "0000-00-00" : source
						.get("faxingshijian"));
		if (source.get("imgURLList") != null) {
			json.put("imgURLList", source.get("imgURLList"));
		}
		if (source.get("bigImgURLList") != null) {
			json.put("bigImgURLList", source.get("bigImgURLList"));
		}
		if (source.get("coverURL") != null) {
			json.put("coverURL", source.get("coverURL"));
		}
		return json;
	}

	public SafeStack<JSONObject> getSafeStack() {
		return safeStack;
	}

	public void consume() {
		JSONObject json = null;
		while ((json = safeStack.pop(10000)) != null) {
			String code = json.getString("code");
			String faxingshijian = json.getString("faxingshijian");
			StringBuilder newsb = FileNameUtil.getDirPath(faxingshijian);
			if(new File(newsb.toString()+code+"/"+code+".over").exists()){
				//已经存在
				continue;
			}
			StringBuilder sb = FileNameUtil.getOldDirPath(faxingshijian, code)
					.append("ok");
			File oldFile = new File(sb.toString());
			if (oldFile.exists()) {
				FileUtil.copyDir(oldFile.getParentFile(), newsb.toString(), false);
				File oldOKfile = new File(newsb.append(code).append("/")
						.append("ok").toString());
				File newOKfile = new File(newsb.toString().replace("ok",
						code + ".over"));
				if(oldOKfile.renameTo(newOKfile)){
					logger.info("{},local copy OK", newsb);
				}
			} else {
				newsb.append(code).append("/");
				String coverURL = (String) json.get("coverURL");
				if (!StringUtil.isBlank(coverURL)) {
					HttpImageUtil.downImage(coverURL, newsb.toString(), 3);
				}
				List<?> imgURLList = (ArrayList<?>) json.get("imgURLList");
				List<?> bigImgURLList = (ArrayList<?>) json
						.get("bigImgURLList");
				downList(bigImgURLList, newsb, 1);
				downList(imgURLList, newsb, bigImgURLList == null ? 2 : 3);
				File newOKfile = new File(newsb.toString()+("/"+
						code + ".over"));
				try {
					if(newOKfile.createNewFile()){
						logger.info("{},处理OK", newsb);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
		}
	}

	private void downList(List<?> imgURLList, StringBuilder newsb, int type) {
		if (imgURLList == null) {
			return;
		}
		for (Object o : imgURLList) {
			String imgUrl = (String) o;
			if(imgUrl.indexOf(config.getMovie_img_prefix())<0){
				imgUrl = config.getMovie_img_prefix()+imgUrl;
			}
			if(imgUrl.indexOf("pl.")>0){//封面
				HttpImageUtil.downImage(imgUrl, newsb.toString(), 3);
			}else if (type == 1) {
				HttpImageUtil.downImage(imgUrl, newsb.toString(), 3);
			} else if (type > 1) {
				HttpImageUtil.downImage(imgUrl, newsb.toString() + "/s/", 3);
				if (type == 2) {
					int lastIndexOf = imgUrl.lastIndexOf("-");
					if (lastIndexOf > 10) {
						String houzui = imgUrl.substring(lastIndexOf,
								imgUrl.length());
						String qian = imgUrl.substring(0,
								imgUrl.lastIndexOf("-"));
						String bigImgUrl = qian + "jp" + houzui;
						HttpImageUtil.downImage(bigImgUrl, newsb.toString(), 3);
					}
				}
			}
		}
	}

}
