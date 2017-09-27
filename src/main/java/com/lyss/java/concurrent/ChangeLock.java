package com.lyss.java.concurrent;
/**
 * 锁对象的改变问题
 */
public class ChangeLock {
	private String lock = "LOCK";

	public void method(){
		//不能直接使用synchronized ("字符串常量") 否则会出现死循环 
		synchronized (lock) {
			try {
					System.out.println(Thread.currentThread().getName()+"begin");
					lock = "CHANGE LOCK"; //值被修改 锁发生了变化
					Thread.sleep(1000);
					
					System.out.println(Thread.currentThread().getName()+"end");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public static void main(String[] args) {
		final ChangeLock stringLock = new ChangeLock();
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
