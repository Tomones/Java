package com.lyss.java.concurrent.volatiles;
/**
 * volatile 关键字的主要作用是使变量在多
 * 个线程间可见
 * 可以保证共享数据的一致性
 */
public class RunThread extends Thread{
	//此处需要使用volatile关键字修饰 才能退出
	private boolean isRunning= true;
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	@Override
	public void run() {
		System.out.println("进入run方法。。。。。");
		while (isRunning) {
			
		}
		System.out.println("线程停止。。。");
	}
	public static void main(String[] args) throws Exception{
		RunThread runThread = new RunThread();
		runThread.start();
		Thread.sleep(2000);
		runThread.setRunning(false);
		System.out.println("isRunning 的值已经设置成了false");
		Thread.sleep(1000);
		System.out.println(runThread.isRunning);
	}
	
}
