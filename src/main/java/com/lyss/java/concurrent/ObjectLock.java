package com.lyss.java.concurrent;
/**
 * synchronized代码块
 */
public class ObjectLock {

	public void method1(){
		synchronized (this) { //对象锁
			try {
				System.out.println("do Method1......");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void method2(){
		synchronized (ObjectLock.class) { //类锁
			try {
				System.out.println("do Method2......");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private ObjectLock objectLock = new ObjectLock();
	public void method3(){
		synchronized (objectLock) { //任意对象锁
			try {
				System.out.println("do Method3......");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		final ObjectLock lock = new ObjectLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.method1();
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
					@Override
					public void run() {
						lock.method2();
					}
				},"t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.method3();
			}
		},"t3");
		t1.start();
		t2.start();
		t3.start();
	}
}
