package com.lyss.java.concurrent;
/**
 * 多线程引发的锁竞争问题
 * 会导致CUP使用率急剧上升
 * 引发宕机事故
 */
public class MyThread1 extends Thread{
	private int count = 5;
	
	@SuppressWarnings("static-access")
	@Override
	public void  run() {
		count --;
		System.out.println(this.currentThread().getName()+"count:"+count);
	}
	public static void main(String[] args) {
		MyThread1 myThread1 = new MyThread1();
		//实例化五个线程 同一时间访问myThread1 count执行--操作
		Thread thread1 = new Thread(myThread1,"t1");
		Thread thread2 = new Thread(myThread1,"t2");
		Thread thread3 = new Thread(myThread1,"t3");
		Thread thread4 = new Thread(myThread1,"t4");
		Thread thread5 = new Thread(myThread1,"t5");
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
	}
}
