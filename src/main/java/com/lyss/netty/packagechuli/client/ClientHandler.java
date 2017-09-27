package com.lyss.netty.packagechuli.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter{


	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			String body = (String) msg;
			
			System.out.println("Client :" + body );
			String response = "收到服务器端的返回信息：" + body;
			System.out.println(response);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
