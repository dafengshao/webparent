package com.github.nfs.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nfs.SpringBeansContext;
import com.github.nfs.db.MongodbAssistor;
import com.github.nfs.model.MongoFile;

public class FileServletHandler extends
		SimpleChannelInboundHandler<FullHttpRequest> {
	/*
	 * if (!"POST".equals(request.getMethod())) { throw new
	 * HttpRequestMethodNotSupportedException(request.getMethod(), new String[]
	 * {"POST"}, "HessianServiceExporter only supports POST requests"); }
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FileServletHandler.class);
	private static final CharSequence CONTENT_LENGTH = "Content-Length";
	
	private MongodbAssistor mongodbAssistor;

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		try {
			if (!request.getDecoderResult().isSuccess()) {
				// TODO sendError();
				return;
			}
			HttpMethod method = request.getMethod();
			if (method != HttpMethod.GET) {
				// TODO sendError()
				return;
			}
			//img
			String uri = request.getUri();
			uri=uri.substring(uri.lastIndexOf("/")+1);
			if(mongodbAssistor==null){
				mongodbAssistor=SpringBeansContext.getBean("mongodbAssistor");
			}
			MongoFile file = mongodbAssistor.find(uri);
			if (file == null) {
				// 404
				return;
			}
			FullHttpResponse response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			buildResponseInfo(response,file);
			response.content().writeBytes(file.getBody());
			response.content().writeBytes("\r\n".getBytes());

			ctx.writeAndFlush(response)
					.addListener(ChannelFutureListener.CLOSE);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
/*Accept-Ranges:bytes
Access-Control-Allow-Origin:*
Age:57114
Cache-Control:max-age=2628000
Connection:keep-alive
Content-Length:11092
Content-Type:image/jpeg
Date:Sat, 24 Dec 2016 17:02:09 GMT
ETag:2f341ffc3c040b4d0f108c8b404370a6
Expires:Mon, 23 Jan 2017 11:02:23 GMT
Last-Modified:Thu, 01 Jan 1970 00:00:00 GMT
Ohc-Response-Time:1 0 0 0 0 0
Server:bfe/1.0.8.13-sslpool-patch
Timing-Allow-Origin:http://www.baidu.com*/
	private void buildResponseInfo(FullHttpResponse response,MongoFile file) {
		HttpHeaders headers = response.headers();
		headers.set("Accept-Ranges", "bytes");
		headers.set("Content-Type", "image/jpeg");
		headers.set("Content-Length", file.getLength());
		
		//headers.set("Last-Modified", file.getLength());
	}
	
	public static void main(String[] args) {
		String url = "sdafsdaf/sdafsdf/sdfs.jpg";
		System.out.println(url.substring(url.lastIndexOf("/")));
	}
}
