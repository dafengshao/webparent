package com.hwf.hessianty.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianExporter;

import com.hwf.hessianty.spring.SpringBootstrap;

public class HessianServiceHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
/*if (!"POST".equals(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request.getMethod(),
					new String[] {"POST"}, "HessianServiceExporter only supports POST requests");
		}*/
	private static final Logger logger = LoggerFactory.getLogger(HessianServiceHandler.class);
	private static final CharSequence CONTENT_LENGTH = "Content-Length";
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		try {
 			CharSequence charSequence = request.headers().get("Content-Type");
			FullHttpResponse response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			if (!"x-application/hessian".equals(charSequence)) {
				logger.info("web request");
				//ByteBuf bb = Unpooled.copiedBuffer(new StringBuffer("hessain server "),CharsetUtil.UTF_8);
				response.content().writeBytes("hessain server ".getBytes());
			} else {
				HttpMethod method = request.method();
				if (!method.equals(HttpMethod.POST)) {
					throw new Exception(
							"HessianServiceExporter only supports POST requests");
				}
				String uri = request.uri();
				CompositeByteBuf content = (CompositeByteBuf) request.content();
				// content.i
				int maxNumComponents = content.maxNumComponents();
				ByteBufAllocator alloc = content.alloc();
				Iterator<ByteBuf> iterator = content.iterator();
				byte[] dst = null;
				int readableBytes = content.readableBytes();
				dst = new byte[readableBytes];
				content.getBytes(content.readerIndex(), dst);
				logger.info("requset size:{}",readableBytes);
				// content.component(0).;
				// content.
				// byte[] array = content.array();
				InputStream is = new ByteArrayInputStream(dst);
				ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
				HessianExporter exporter = SpringBootstrap.getBean(uri);// spring
																		// 上下文中获取
				//
				exporter.invoke(is, output);

				// ByteBuf bb = Unpooled.buffer();
				byte[] byteArray = output.toByteArray();
				logger.info(new String(byteArray));
				// bb.writeBytes(byteArray);
				response.content().writeBytes(byteArray);
				// response.headers().set
				// response.content().writeBytes("\r\n".getBytes());
			}
			response.headers().setObject(CONTENT_LENGTH,
					response.content().readableBytes());
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

