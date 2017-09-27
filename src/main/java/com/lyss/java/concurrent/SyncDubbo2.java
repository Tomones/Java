package com.lyss.java.concurrent;

/**
 * synchronized 的重入
 *
 */
public class SyncDubbo2 {

	static class Main{
		public int i = 10;
		public synchronized void operationsup(){
			try {
				i--;
				System.out.println("Main 輸出I的值為："+i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static class Sub extends Main{
		public synchronized void operationsub(){
			try {
				while (i>0) {
					i--;
					System.out.println("Sub 輸出I的值為："+i);
					this.operationsup();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Sub sub = new Sub();
				sub.operationsub();
			}
		});
		thread.start();
	}
}
