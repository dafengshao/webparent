package com.github.nfs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nfs.handler.FileServletHandler;

public class HttpServer {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
	
	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	ServerBootstrap strap = null;
	//Executor exeutor = Executors.newCachedThreadPool(threadFactory)
	
	public void run() throws InterruptedException{

		try{
			strap = new ServerBootstrap();
			strap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					
					logger.info("当前线程id:{},name:{},ChannelInitializer:{}",
							Thread.currentThread().getId(),
							Thread.currentThread().getName()
							);
					ch.pipeline().addLast("http-decoder",new HttpRequestDecoder())
					.addLast("http-aggregator",new HttpObjectAggregator(1024*512))//512KB
					.addLast("http-encoder",new HttpResponseEncoder())
					.addLast("http-chunked", new ChunkedWriteHandler())
					//.addLast("http-logger", new LoggingHandler())
					.addLast("http-file-servlet",new FileServletHandler())
					;
				}
			});
			ChannelFuture future = strap.bind(host, port).sync();
			logger.info("http file server start ok .host:{},port:{}.",host,port);
			future.channel().closeFuture().sync();
		}finally{
			shutdown();
		}
	}
	private void shutdown() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
	
}
