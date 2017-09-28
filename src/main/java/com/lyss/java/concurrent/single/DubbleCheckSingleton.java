package com.lyss.java.concurrent.single;

public class DubbleCheckSingleton {

	private static DubbleCheckSingleton ds;
	//懶加載模式
	public static DubbleCheckSingleton getInstance(){
		if (ds == null) {
			try {
				//模拟对象准备时间
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			synchronized (DubbleCheckSingleton.class) {
				//如果這裡不加判斷，就會成為多例模式
				if (ds == null) {
					ds = new DubbleCheckSingleton();
				}
			}
		}
		return ds;
	}
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(DubbleCheckSingleton.getInstance().hashCode());
				
			}
		},"t1").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(DubbleCheckSingleton.getInstance().hashCode());
				
			}
		},"t1").start();
		new Thread(new Runnable() {
	
			@Override
			public void run() {
				System.out.println(DubbleCheckSingleton.getInstance().hashCode());
				
			}
		},"t1").start();
			}
}
