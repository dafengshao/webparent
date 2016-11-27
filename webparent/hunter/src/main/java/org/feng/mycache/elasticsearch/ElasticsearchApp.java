package org.feng.mycache.elasticsearch;

import java.net.InetAddress;
import java.util.Date;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.queryparser.xml.FilterBuilderFactory;
import org.apache.lucene.queryparser.xml.builders.TermsFilterBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Hello world!
 *
 */
public class ElasticsearchApp {
	static Client client = null;
	static String index = "pages", type="sina";
	public static void main(String[] args) throws Exception {
		/* 创建客户端 */
		// client startup
		client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));
		// 创建索引
		//client.admin().indices().prepareCreate("pages").execute().actionGet();
		// 创建索引结构
		/*XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
				.startObject("sina").startObject("properties")
				.startObject("article_title").field("type", "string")
				.field("store", "yes").field("analyzer", "ik")
				.field("index", "analyzed").endObject()
				.startObject("article_content").field("type", "string")
				.field("store", "no").field("analyzer", "ik")
				.field("index", "analyzed").endObject()
				.startObject("article_url").field("type", "string")
				.field("store", "yes").field("index", "not_analyzed")
				.endObject().endObject().endObject().endObject();
		PutMappingRequest mapping = Requests.putMappingRequest("pages")
				.type("sina").source(builder);
		PutMappingResponse response = client.admin().indices()
				.putMapping(mapping).actionGet();
		System.out.println(response);*/
		
		
		
		JSONObject json = new JSONObject();
		json.put("article_title", "金九银十到来市场回暖 深圳新房均价重回6字头");
		json.put("article_content", "随着传统周期 “ 金九银十 ” 的到来，深圳二手房市场反应较快，相对 5、6 月份成交量回升明显，环比分别上升 16.7% 和 13.6%。新房方面，尽管上周（9.05-9.11）一手住宅成交 584 套，但成交套数小幅高于 8 月周均成交套数 523 套，处于 5 月份以来的中等水平。同时受南山区成交占比回落，上周全市新房均价回归 6 字头，为 60225 万 /㎡。");
		json.put("article_url", "http://shenzhen.sina.com.cn/news/2016-09-13/detail-ifxvukhx4989389.shtml");
		json.put("categrey", "深圳");
		json.put("createTime", new Date().getTime());
		//addIndex(index, type, null, json);
		
		//获取索引数据  根据id获取  
		/*GetResponse response = client.prepareGet("pages", "sina", "AVciqJfpYAUNxWyllTNb")
		        .execute()
		        .actionGet();
		byte[] sourceAsBytes = response.getSourceAsBytes();
		Object parse = JSONObject.parse(sourceAsBytes, com.alibaba.fastjson.parser.Feature.values());
		
		System.out.println(parse);
		*/
		//删除索引数据
		/*DeleteResponse actionGet = client.prepareDelete("pages", "sina", "AVcilWnLYAUNxWyllTNP")
		        .execute()
		        .actionGet();*/
		//
		/*JSONObject json = new JSONObject();
		//json.put("article_title", "这个书院成立初就不一般");
		JSONArray jsnar = new JSONArray();
		jsnar.add("时政");
		jsnar.add("教育");
		json.put("categrey",jsnar);
		//json.put("listInfo", new JSONArray().add("sfsd"));
		//IndexRequest indexRequest = new IndexRequest("pages", "sina", "AVcil4m2YAUNxWyllTNV");
		//更新索引数据
		UpdateRequest updateRequest = new UpdateRequest("pages", "sina", "AVcil4m2YAUNxWyllTNV")
			.doc(json.toJSONString());
		UpdateResponse updateResponse = client.update(updateRequest).get();*/
		
		
		//多个id获取
		/*MultiGetResponse multiGetItemResponses = client.prepareMultiGet().add("pages", "sina", "AVcil4m2YAUNxWyllTNV","AVcjRiM3f7OWXXE8lDv9").get();
		multiGetItemResponses.getResponses();*/
		
		//一个id获取
		/*GetResponse response = client.prepareGet("pages", "sina", "AVciqJfpYAUNxWyllTNb")
		        .execute()
		        .actionGet();*/
		//综合搜索
		
		//restful
		/*JSONObject queryJSON = new JSONObject();
		JSONObject feildJSON = new JSONObject();
		JSONObject termJSON = new JSONObject();
		feildJSON.put("article_title", "这个");
		termJSON.put("term", feildJSON);
		queryJSON.put("query", termJSON);
		SearchResponse response = client.prepareSearch("pages") 
        .setTypes("sina").setSource(queryJSON.toJSONString()).execute().get();*/
		
		
		
		SearchResponse response = client.prepareSearch("pages")  
        .setTypes("sina")  
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)  
        //.setQuery(QueryBuilders.fieldMaskingSpanQuery("name", "张三"))    
        //"createTime": 1473767418915,
        //过滤
         .setPostFilter(QueryBuilders.rangeQuery("createTime").from(1473767315597L).to(1473767418915L))// Filter
        //搜索
         //.setQuery(QueryBuilders.matchQuery("article_title", "深圳"))
        .setFrom(0).setSize(10).setExplain(true)  //排序
        .addSort(SortBuilders.fieldSort("createTime").order(SortOrder.ASC))
        
        .addFields("article_title","createTime")
        .execute()  
        .actionGet();  
		
		SearchHits hits = response.getHits();  
		int i=0;
		while(i<hits.getTotalHits()){
			System.out.println(hits.getAt(i).getId());
			//Object parse = JSONObject.parse(hits.getAt(i).getSourceRef().array(), com.alibaba.fastjson.parser.Feature.values());
			//parse = JSONObject.toJSON();
			System.out.println(hits.getAt(i).field("article_title").getValue());
			i++;
		}
		client.close();
	}
	
	
	public static String addIndex(String index,String type,String id,JSONObject json){
		//添加索引数据
				/*JSONObject json = new JSONObject();
				json.put("article_title", "前海2679套人才住房配租 每月1600元可租两房一厅");
				json.put("article_content", "南方日报讯 （记者/张玮）9月12日，深圳市前海管理局正式发布“前海合作区2016年人才住房配租方案”（以下简称“方案”），启动龙海家园人才住房配租。本次配租的房源共2679套。其中，包括产业扶持住房1904套；深港合作企业和港籍人才775套，约占近三成。记者注意到，这些套房从35-50平方米不等，月基准租金在1120-1600元之间。");
				json.put("article_url", "http://shenzhen.sina.com.cn/news/n/2016-09-13/detail-ifxvukhx4986315.shtml");
				json.put("categrey", "深圳");*/
				IndexResponse indexResponse = client.prepareIndex("pages", "sina", id)
						.setSource(json.toJSONString())
						.execute()
						.actionGet();
			return indexResponse.getId();		
	}
}
