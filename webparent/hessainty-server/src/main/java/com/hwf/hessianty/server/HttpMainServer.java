package com.hwf.hessianty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpMainServer {
	private static final Logger logger = LoggerFactory.getLogger(HttpMainServer.class);
	public void run(int port,String ip) throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap strap = new ServerBootstrap();
			strap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder",new HttpRequestDecoder())
					.addLast("http-aggregator",new HttpObjectAggregator(1024*512))//512KB
					.addLast("http-encoder",new HttpResponseDecoder())
					.addLast("http-chunked", new ChunkedWriteHandler())
					.addLast("http-servlet",new HessianServiceHandler());
				}
			});
					
			ChannelFuture future = strap.bind(ip, port).sync();
			logger.info("http服务器启动成功,ip={},port={}",ip,port);
			future.channel().closeFuture().sync();
		}finally{
			bossGroup.close();
			workerGroup.close();
		}
	}
	
	public static void main(String[] args) {
		HttpMainServer mainserver = new HttpMainServer();
		int port=9527;
		String ip = "192.168.0.126";
		try {
			mainserver.run(port, ip);
		} catch (Exception e) {
			logger.info("http服务器启动异常,ip={},port={}",ip,port,e);
		}
	}
}
