package com.lyss.SocketIO.bio2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerExecutorPool {

	
	private ExecutorService executor;
	public HandlerExecutorPool(int maxPoolSize, int queueSize){
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				maxPoolSize, 
				120L, 
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
		/**
		 * ArrayBlockingQueue
		 * 一个由数组支持的有界阻塞队列。
		 * 此队列按 FIFO（先进先出）原则对元素进行排序。
		 * 队列的头部 是在队列中存在时间最长的元素。
		 * 队列的尾部 是在队列中存在时间最短的元素。
		 * 新元素插入到队列的尾部，队列检索操作则是从队列头部开始获得元素。
		 * 中文名 有界阻塞队列
		 */
	}
	
	public void execute(Runnable task){
		this.executor.execute(task);
	}
	
	
	
}
