package com.github.nfs.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nfs.db.MongodbAssistor;
import com.github.nfs.model.MongoFile;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

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
			String uri = request.getUri();
			MongoFile file = mongodbAssistor.find(uri);
			if (file == null) {
				// 404
				return;
			}
			HttpHeaders httpHeaders = buildResponseHeaders(file);
			// CharSequence charSequence =
			// request.headers().get("Content-Type");
			FullHttpResponse response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response.headers().set(httpHeaders);
			response.content().writeBytes(file.getBody());
			response.content().writeBytes("\r\n".getBytes());
			// response.headers().set

			ctx.writeAndFlush(response)
					.addListener(ChannelFutureListener.CLOSE);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private HttpHeaders buildResponseHeaders(MongoFile file) {
		// TODO Auto-generated method stub
		return null;
	}
}
