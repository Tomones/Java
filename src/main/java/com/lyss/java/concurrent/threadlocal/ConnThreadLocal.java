package com.lyss.java.concurrent.threadlocal;
/**
 * ThreadLocal 只是针对在同一个线程中来说的
 */
public class ConnThreadLocal {

	public static ThreadLocal<String> tLocal = new ThreadLocal<>();
	
	public void setTlocal(String value){
		tLocal.set(value);
	}
	public void getLocal(){
		System.out.println(Thread.currentThread().getName()+":"+this.tLocal.get());
	}
	public static void main(String[] args) {
		final ConnThreadLocal local = new ConnThreadLocal();
		new Thread(new Runnable() {
			@Override
			public void run() {
				local.setTlocal("郭涛");
				local.getLocal();
			}
		},"t1").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				local.getLocal();
			}
		},"t2").start();
	}
}
