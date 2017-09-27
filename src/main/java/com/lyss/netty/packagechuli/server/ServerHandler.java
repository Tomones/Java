package com.lyss.netty.packagechuli.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {


	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server channel active... ");
	}


	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
			String body = (String) msg;
			System.out.println("Server :" + body );
			String response = "进行返回给客户端的响应：" + body+"$_" ;
			ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()))
			.addListener(ChannelFutureListener.CLOSE);
	}

	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("读完了");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
			throws Exception {
		ctx.close();
	}

}
