package com.lyss.java.queue;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
/**
 * 使用wait和notify设计一个简单的阻塞有界队列
 * @author Administrator
 * 主要实现两个方法
 * 		take() 和 put()
 */
import java.util.concurrent.atomic.AtomicInteger;
public class MyBlockQueue {

	//创建一个集合装载元素
	private final LinkedList<Object> list = new LinkedList<>();
	//增加一个计数器
	private AtomicInteger count = new AtomicInteger(0);
	//指定容器的上限和下限
	private final int minSize = 0;
	private final int maxSize;
	
	//初始化时指定队列的最大长度
	public MyBlockQueue(int maxSize){
		this.maxSize = maxSize;
	}
	//初始化一个对象 用于加锁
	private final Object lock = new Object();
	//put()
	public void put(Object object){
		synchronized (lock) {
			if (count.get() == this.maxSize) {
				try {
					System.out.println("队列满了。。。。。");
					//如果队列空间满了，就阻塞着
					lock.wait();
				} catch (InterruptedException e) {
					// TO-generated catch blockDO Auto
					e.printStackTrace();
				}
			}
			list.add(object);
			System.out.println("新加入的元素为："+object);
			//计数器统计
			count.incrementAndGet();
			//通知另外一个线程（唤醒）
			lock.notify();
		}
		
	}
	//take()
	public Object take(){
		Object ret = null;
		synchronized (lock) {
			while (count.get() == this.minSize) {
				try {
					System.out.println("队列空了。。。。。");
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//移除第一个元素并赋值给Object
			ret =list.removeFirst();
			//计数器--
			count.decrementAndGet();
			lock.notify();
			
		}
		return ret;
		
	}
	public int size(){
		return count.get();
	}
	
	public static void main(String[] args) {
		final MyBlockQueue queue = new MyBlockQueue(2);
		queue.put("1");
		queue.put("2");
		System.out.println("当前容器的长度"+queue.size());
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				queue.put("3");
				queue.put("4");
				
			}
		},"t1").start();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			public void run() {
				Object take1 = queue.take();
				System.out.println("移除的元素为："+take1);
				Object take2 = queue.take();
				System.out.println("移除的元素为："+take2);
				Object take3 = queue.take();
				System.out.println("移除的元素为："+take3);
				Object take4 = queue.take();
				System.out.println("移除的元素为："+take4);
			}
		},"t2").start();
	}
}
