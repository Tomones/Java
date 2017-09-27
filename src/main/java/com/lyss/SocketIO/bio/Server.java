package com.lyss.SocketIO.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统BIO编程
 * @author Administrator
 *
 */

public class Server {
	
	final static int PORT = 6379;
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server 启动。。。。");
			//进行阻塞
			Socket accept = serverSocket.accept();
			//新建一个线程执行客户端任务
			/**（此处会出现一个问题
				当请求变多是，服务端需要开启更多的线程来处理请求内容
				会导致服务器宕机
				linux 开的线程数是Windows的两倍）*/
			new Thread(new ServerHandler(accept)).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (serverSocket!=null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				serverSocket= null;
			}
		}
	}

}
