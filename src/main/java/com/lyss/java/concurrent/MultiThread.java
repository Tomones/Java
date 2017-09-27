package com.lyss.java.concurrent;
/**
 * 多个线程多个锁
 * 多个线程 每个线程都能拿到自己指定的锁，分别获得锁之后，
 * 执行synchronized方法体内的内容
 */
public class MultiThread {
	private static int num = 0;
	//static synchronized 加上该修饰符之后，锁变成类级别锁
	//意思是只有一把锁
	//无论有多少对象都可以同步
	private synchronized void printNum(String tag) {
		try {
			if (tag.equals("a")) {
				num = 100;
				System.out.println("Tag A,值设置完毕。。。");
				Thread.sleep(1000);
			}else {
				num = 200;
				System.out.println("Tag B,值设置完毕。。。");
			}
			System.out.println("Tag:"+tag+"----"+"num:+"+num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		//创建两个不同的对象
		//一个对象有一把锁(对象锁)
		final MultiThread myThread1 = new MultiThread();
		final MultiThread myThread2 = new MultiThread();
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				myThread1.printNum("a");
				
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				myThread2.printNum("b");
			}
		});
		thread1.start();
		thread2.start();
	}
}
