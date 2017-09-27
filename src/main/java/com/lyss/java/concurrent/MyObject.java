package com.lyss.java.concurrent;
/**
 * 对象锁的同步和异步问题
 */
public class MyObject {

	//同步方法
	private synchronized void method1() {
		try {
			System.out.println(Thread.currentThread().getName()+"===Method1");
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	//异步方法
	public void method2(){
		System.out.println(Thread.currentThread().getName()+"===Method2");
	}
	public static void main(String[] args) {
		//只创建了一个对象（一把锁）
		final MyObject object = new MyObject();
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				object.method1();
			}
		},"t1");
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				object.method2();
			}
		},"t2");
		thread1.start();
		thread2.start();
	}
}
