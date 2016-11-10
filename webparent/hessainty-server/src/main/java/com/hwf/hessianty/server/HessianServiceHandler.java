package com.hwf.hessianty.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

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
			int maxNumComponents = content.maxNumComponents();
			content.component(0).;
			//content.
			byte[] array = content.array();
			InputStream is = new ByteArrayInputStream(array);
			ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
			HessianExporter exporter = SpringBootstrap.getBean(uri);//spring 上下文中获取
			
				exporter.invoke(is, output);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

