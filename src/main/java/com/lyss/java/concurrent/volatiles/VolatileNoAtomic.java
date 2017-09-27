package com.lyss.java.concurrent.volatiles;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileNoAtomic extends Thread{

	//private static volatile int count;
	//使用原子类进行原子操作(参数表示初始化的值)
	private static AtomicInteger count = new AtomicInteger(0);
	private static void addCount(){
		for (int i = 0; i < 1000; i++) {
			//count++;
			count.incrementAndGet(); //++
		}
		System.out.println(count);
	}
	@Override
	public void run() {
		addCount();
	}
	public static void main(String[] args) {
		VolatileNoAtomic[] arr = new VolatileNoAtomic[10];
		//将VolatileNoAtomic对象放入数组中
		for (int i = 0; i < 10; i++) {
			arr[i] = new VolatileNoAtomic();
		}
		//循环遍历10次 启动每一个对象
		for (int i = 0; i < 10; i++) {
			arr[i].start();
		}
		/**
		 * 从输出结果看 
		 * 多线程并发调用时
		 * 最后打印的值并不是10000
		 * 所以被volatile关键字修饰的变量值并没有实现一致性
		 */
	}
}
