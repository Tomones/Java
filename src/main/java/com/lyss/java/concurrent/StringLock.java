package com.lyss.java.concurrent;
/**
 * 尽量不要使用String的常量加锁
 * 否则会出现死循环问题
 */
public class StringLock {

	public void method(){
		//不能直接使用synchronized ("字符串常量") 否则会出现死循环 
		synchronized (new String("字符串常量")) {
			try {
				while (true) {
					System.out.println(Thread.currentThread().getName()+"begin");
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName()+"end");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public static void main(String[] args) {
		final StringLock stringLock = new StringLock();
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				stringLock.method();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				stringLock.method();
			}
		}).start();
		
	}
}
