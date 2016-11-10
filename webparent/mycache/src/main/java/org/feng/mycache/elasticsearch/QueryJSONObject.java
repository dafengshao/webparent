package org.feng.mycache.elasticsearch;

import com.alibaba.fastjson.JSONObject;

public class QueryJSONObject extends JSONObject{
	/*{
	    "query": {
	        "term": {
	            "intro": "北京"
	        }
	    },
	    "from": 0,
	    "size": 20,
	    "sort": {
	        "age": {
	            "order": "asc"
	        },
	        "birthday": {
	            "order": "desc"
	        }
	    },
	    "highlight": {
	        "pre_tags": [
	            "<tag1>",
	            "<tag2>"
	        ],
	        "post_tags": [
	            "</tag1>",
	            "</tag2>"
	        ],
	        "fields": {
	            "intro": {}
	        }
	    }
	}*/
	
	
	/*查询intro包含"研究"或者"方鸿渐"
	{
	    "query": {
	        "query_string": {
	            "fields": [
	                "intro"
	            ],
	            "query": "研究方鸿渐"
	        }
	    },
	    "sort": [
	        {
	            "birthday": {
	                "order": "asc"
	            }
	        }
	    ]
	}*/
	

	public JSONObject putSelf(String key, Object value) {
		super.put(key, value);
		return this; 
	}
	public QueryJSONObject(JSONObject json,int from,int size){
		super();
		this.put("query", json);
		this.put("from", from);
		this.put("size"	,size);
	}
	
	public JSONObject bulidHighlight(JSONObject json){
		this.put("highlight", json);
		return this;
	}
	
	public JSONObject buildSort(JSONObject json){
		this.put("sort", json);
		return this;
	}
}
