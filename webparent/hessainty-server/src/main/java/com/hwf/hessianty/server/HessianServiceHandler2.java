package com.hwf.hessianty.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HessianServiceHandler2 extends ChannelHandlerAdapter{

	private static final Logger logger = LoggerFactory.getLogger(HessianServiceHandler2.class);
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
		logger.info("messageReceived,ChannelHandlerContext={},Object={}",ctx,msg);
		
	}
	
	
	protected void messageReceived(ChannelHandlerContext ctx, HttpRequest msg)
			throws Exception {
		logger.info("messageReceived,ChannelHandlerContext={},Object={}",ctx,msg);
	}
}
