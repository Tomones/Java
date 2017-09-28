package com.lyss.java.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import com.lyss.java.util.ArrayList;

public class UseQueue {

	public static void main(String[] args) throws Exception{
		//高性能无阻塞无界队列 ConcurrentLinkedQueue
		/**ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
		queue.add("a");
		queue.add("b");
		queue.offer("c");
		queue.offer("d");
		
		System.out.println(queue.size());
		System.out.println(queue.poll()); //从队列头部取出元素并删除
		System.out.println("queue.poll():"+queue.size()); //4
		System.out.println(queue.peek());//b
		System.out.println("queue.peek():"+queue.size());//4
		System.out.println(queue.remove());
		System.out.println("queue.remove():"+queue.size());*/
		
		//阻塞队列
		/**LinkedBlockingQueue<Object> bqueue = new LinkedBlockingQueue<>(10);
		bqueue.offer("a");
		bqueue.offer("b");
		bqueue.offer("c");
		bqueue.offer("d");
		bqueue.offer("e");
		ArrayList<Object> arrayList = new ArrayList<>();
		//表示把队列中的前三个元素放入集合中
		System.out.println(bqueue.drainTo(arrayList,3));
		System.out.println(arrayList.size());
		for (Object object : arrayList) {
			System.out.println("*****************"+object);
		}*/
	/**ArrayBlockingQueue<Object> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
		//arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS);表示在两秒钟之内加入a元素
		//		如果加入成功则返回true 否则返回false
		arrayBlockingQueue.add("c");
		arrayBlockingQueue.add("d");
		arrayBlockingQueue.add("e");
		boolean offer1 = arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS);
		boolean offer2 = arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS);
		System.out.println(offer1);
		System.out.println(offer2);*/
		
		SynchronousQueue<String> queue = new SynchronousQueue<>();
		queue.add("ss");
	}
}
