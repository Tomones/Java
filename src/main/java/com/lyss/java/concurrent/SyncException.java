package com.lyss.java.concurrent;
/**
 * synchronized 異常
 * 
 * ‘存储过程 不写begin 和 end 无法写Exception’
 */
public class SyncException {

	private int i = 0;
	public synchronized void operation(){
		while (true) {
			try {
				i++;
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName()+",i="+i);
				if (i == 10) {
					Integer.parseInt("a");
				}
			} catch (Exception e) {
				e.printStackTrace();
				//記錄日誌
				System.out.println("Log info i="+i);
				continue;
				//拋出運行時異常 可以打斷程序運行
				//throw new RuntimeException();
			}
			
		}
	}
	public static void main(String[] args) {
		final SyncException syncException = new SyncException();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				syncException.operation();
			}
		},"t1");
		thread.start();
	}
	
}
