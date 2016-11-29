package com.hwf.avx.config;

import java.util.HashMap;
import java.util.Map;

public class PrefixConfig {

	/** https://avmo.pw/cn/movie/5p3y */
	public static final String mo_movie_url_prefix = "https://avmo.pw/cn/movie/";
	/** https://jp.netcdn.space/digital/video/cesd00271/cesd00271ps.jpg */
	public static final String mo_movie_img_url_perfix = "https://jp.netcdn.space/digital/video/";
	/***/
	public static final String mo_url = "https://avmo.pw/cn/released/page/";
	/** https://jp.netcdn.space/mono/actjpgs/hatano_yui.jpg */
	public static final String mo_actor_avatar_img_url_prefix = "https://jp.netcdn.space/mono/actjpgs/";
	/** https://avmo.pw/cn/star/2jv **/
	public static final String mo_actor_url_prefix = "https://avmo.pw/cn/star/";

	/*****************************************************************************************/

	/** https://avso.pw/cn/movie/3ss */
	public static final String so_movie_url_prefix = "https://avso.pw/cn/movie/";
	/** https://avso.pw/cn/star/3qz */
	public static final String so_actor_url_prefix = "https://avso.pw/cn/star/";
	/** https://jp.netcdn.space/digital/video/cesd00271/cesd00271ps.jpg */
	public static final String so_movie_img_url_perfix = "https://us.netcdn.space/ave/archive/VodImages/screenshot/";
	public static final String so_movie_cover_img_url_perfix = "https://us.netcdn.space/1p/moviepages/";
	/***/
	public static final String so_url = "https://avso.pw/cn/released/page/";
	/** https://us.netcdn.space/mth/media/cast/6169/thumbnail.jpg */
	public static final String so_actor_avatar_img_url_prefix = "https://us.netcdn.space/ave/ActressImage/LargeImage/";

	String movie_each_prefix = "";
	String movie_url_prefix = "";
	String movie_img_prefix = "";
	String actor_url_prefix = "";
	String actor_img_prefix = "";
	String db = "";
	String movieTable="";
	String actorTable="";
	AvType avType;
	
	
	
	public PrefixConfig(AvType avType) {
		this.avType = avType;
		switch (avType) {
		case mo:
			db = "avmo.pw(new)";
			movieTable = "movie";
			actorTable = "actor(new)";
			movie_each_prefix = "https://avmo.pw/cn/released/page/";
			movie_url_prefix = "https://avmo.pw/cn/movie/";
			movie_img_prefix = "https://jp.netcdn.space/digital/video/";
			actor_url_prefix = "https://avmo.pw/cn/star/";
			actor_img_prefix = "https://jp.netcdn.space/mono/actjpgs/";
			break;
		case so:
			db = "avso.pw(soso)";
			movieTable = "movie";
			actorTable = "actor";
			movie_each_prefix = "https://avso.pw/cn/released/page/";
			movie_url_prefix = "https://avso.pw/cn/movie/";
			actor_url_prefix = "https://avso.pw/cn/star/";
			movie_img_prefix = "https://us.netcdn.space/ave/new/jacket_images/"//小封面
					+ ",https://us.netcdn.space/ave/archive/VodImages/screenshot/"//影片截图
					+ ",https://us.netcdn.space/ave/archive/bigcover/"//大封面
					+ ",https://us.netcdn.space/he/contents/"//封面包括大小
					+ ",https://us.netcdn.space/mth/media/";//大小封面
			actor_img_prefix = "https://us.netcdn.space/he/actorprofile/"
					+ ",https://us.netcdn.space/ave/ActressImage/LargeImage/"
					+ ",https://us.netcdn.space/mth/media/cast/";
			break;
		case xo:
			movie_url_prefix = "";
			movie_img_prefix = "";
			actor_url_prefix = "";
			actor_img_prefix = "";
			break;
		case testso:
			db = "test";
			movieTable = "movie";
			actorTable = "actor";
			movie_each_prefix = "https://avso.pw/cn/released/page/";
			movie_url_prefix = "https://avso.pw/cn/movie/";
			actor_url_prefix = "https://avso.pw/cn/star/";
			break;
		case testmo:
			db = "test";
			movieTable = "movie";
			actorTable = "actor";
			movie_each_prefix = "https://avmo.pw/cn/released/page/";
			movie_url_prefix = "https://avmo.pw/cn/movie/";
			actor_url_prefix = "https://avmo.pw/cn/star/";
			/*
			movie_img_prefix = "https://jp.netcdn.space/digital/video/";
			actor_img_prefix = "https://jp.netcdn.space/mono/actjpgs/";*/
			break;
		}
	}

	public AvType getAvType() {
		return avType;
	}

	public void setAvType(AvType avType) {
		this.avType = avType;
	}

	public String getMovie_url_prefix() {
		return movie_url_prefix;
	}

	public String getMovie_img_prefix() {
		return movie_img_prefix;
	}

	public String getActor_url_prefix() {
		return actor_url_prefix;
	}

	public String getActor_img_prefix() {
		return actor_img_prefix;
	}
	
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getMovieTable() {
		return movieTable;
	}

	public void setMovieTable(String movieTable) {
		this.movieTable = movieTable;
	}

	public String getActorTable() {
		return actorTable;
	}

	public void setActorTable(String actorTable) {
		this.actorTable = actorTable;
	}



	public static enum AvType {
		mo("avmo", "https://avmo.pw/cn/")
		, so("avso", "https://avso.pw/cn/")
		, xo("avxo", "https://avxo.pw/cn/")
		,testmo("test","https://avmo.pw/cn/")
		,testso("testso","https://avso.pw/cn/");

		private String key;
		private String url;

		private AvType(String key, String url) {
			this.key = key;
			this.url = url;
		}

		public String getKey() {
			return key;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public static Map<String, AvType> getAvTypeMap() {
			Map<String, AvType> map = new HashMap<String, AvType>();
			for (AvType e : AvType.values()) {
				map.put(String.valueOf(e.getKey()), e);
			}
			return map;
		}
	}
	public static enum URLType{
		avtor,
		movie,
		avtorAvatarImg,
		movieSmallCoverImg,
		movieNormalCoverImg,
		movieSmallScreenshotImg,
		movieBigScreenshotImg
	}
}


