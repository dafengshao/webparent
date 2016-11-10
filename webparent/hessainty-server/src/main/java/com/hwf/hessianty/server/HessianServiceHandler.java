package com.hwf.hessianty.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.caucho.hessian.server.HessianSkeleton;
import com.hwf.hessianty.spring.SpringBootstrap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public class HessianServiceHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
/*if (!"POST".equals(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request.getMethod(),
					new String[] {"POST"}, "HessianServiceExporter only supports POST requests");
		}*/
	private static final Logger logger = LoggerFactory.getLogger(HessianServiceHandler.class);
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		try {
			HttpMethod method = request.method();
			if(!method.equals(HttpMethod.POST)){
				throw new Exception("HessianServiceExporter only supports POST requests");
			}
			String uri = request.uri();
			CompositeByteBuf content = (CompositeByteBuf) request.content();
			//content.i
			int maxNumComponents = content.maxNumComponents();
			Iterator<ByteBuf> iterator = content.iterator();
			byte[] dst =null;
			while(iterator.hasNext()){
				ByteBuf next = iterator.next();
				next.isDirect();
				ByteBuffer nioBuffer = next.nioBuffer();
				dst = new byte[next.capacity()];
				//nioBuffer.array();
				next.readBytes(dst);
				logger.info(new String(dst));
			}
			//content.component(0).;
			//content.
//			byte[] array = content.array();
			InputStream is = new ByteArrayInputStream(dst);
			ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
			HessianExporter exporter = SpringBootstrap.getBean(uri);//spring 上下文中获取
//			
				exporter.invoke(is, output);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

