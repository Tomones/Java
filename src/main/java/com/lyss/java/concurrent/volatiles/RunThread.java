package com.lyss.java.concurrent.volatiles;
/**
 * volatile 关键字的主要作用是使变量在多个线程间可见
 * 可以保证共享数据的一致性
 * 
 *JDK优化（对每一个Thread分配了内存空间，用来存放当前线程使用时所需要的一些变量等资源的副本）：
 *	线程在执行过程中，会被单独的分配一块空间 ，会将变量从主内存中copy到线程内存中
 *	以提高效率
 *  所以在多个线程访问同一个变量时，会出现变量值不一致问题
 *  加入volatile关键字可以使线程执行过程中，在调用被volatile关键字修饰的变量时
 *  强制从主内存中读取
 *  从而保证值的一致性 
 *  
 *  volatile只具备可见性，不具备原子性
 */
public class RunThread extends Thread{
	//此处需要使用volatile关键字修饰 才能退出
	private volatile  boolean isRunning= true;
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	@Override
	public void run() {
		System.out.println("进入run方法。。。。。");
		while (isRunning) {
			//...
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
