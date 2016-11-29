package com.hwf.avx.elsearch;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ElsQueryClient extends ElsClient{

	private static Logger logger = LoggerFactory.getLogger(ElsQueryClient.class);
	
	public ElsQueryClient(String host, int port) throws Exception {
		super(host, port);
	}
	
	public SearchResponse query(String index,String type,String queryCon) throws IOException {
		SearchRequestBuilder builder = client.prepareSearch(index).setTypes(type);
		builder.setSearchType(SearchType.SCAN);
		builder.setScroll(new TimeValue(10*60*1000));
		builder.setSize(200);
		//builder.setSource(queryCon.getBytes());
		builder.setQuery(queryCon.getBytes());
		ListenableActionFuture<SearchResponse> execute = builder.execute();
		SearchResponse actionGet = execute.actionGet();
		return actionGet;
	}
	
	
	public SearchResponse query(ScrollSearch search) throws IOException {
		ListenableActionFuture<SearchResponse> execute  = null;
		if(search.getPageNo()==1&&search.getSrollId()==null){
			SearchRequestBuilder builder  = client.prepareSearch(search.getIndex())
					.setTypes(search.getType());
			builder.setScroll(search.getTimeValue());
			builder.setSize(search.getSize());
			builder.setQuery(search.getQueryJSON().getBytes());
			execute = builder.execute();
		}else{
			if(search.getSrollId()!=null){
				SearchScrollRequestBuilder sbuilder = client.prepareSearchScroll(search.getSrollId())
					.setScroll(search.getTimeValue());
				execute = sbuilder.execute();
			}else{
				return null;
			}
		}
		logger.info("search page no:{}",search.getPageNo());
		SearchResponse response = execute.actionGet();
		return response;
	}
	
	

	
	
	
	
	static String index = "avmo.pw(new)";
	static String type = "movie";
//	public static void main1(String[] args) throws Exception {
//		 ElsQueryClient client  = new ElsQueryClient("127.0.0.1", 9300);
//		 ScrollSearch search = new ScrollSearch();
//		 search.setSize(2)
//		 	.setIndex()
//		 	.setType("movie")
//		 	.setQueryJSON(q.toJSONString());
//		 SearchResponse queryResponse = client.query(search);
//		 String scrollId = queryResponse.getScrollId();
//		 
//		 
//		 
//		 
//		 
//		 System.out.println(queryResponse);
//		 client.close();
//	} 
}
