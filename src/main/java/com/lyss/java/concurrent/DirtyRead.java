package com.lyss.java.concurrent;
/**
 * 脏读
 * 
 * 该类中共有两个线程在同时执行
 * 主线程和Thread1
 * 此案例的目的在于 当一个线程设置值时不希望别的线程去调用值
 * 所以需要在两个方法上加synchronized 方法
 * 因为此案例中只有一个对象，所以只有一把锁
 */
public class DirtyRead {
	private String username = "guotao";
	private String password = "guotao123";
	
	public synchronized void setValue(String username, String password){
		this.username = username;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.password = password;
		System.out.println("SetValue 的最终结果是：username = "+username+","+"password ="+password);
	}
	
	public  void getValue(){
		System.out.println("getValue 得到的结果是：username = "+this.username+","+"password ="+this.password);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		final DirtyRead dirtyRead = new DirtyRead();
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				dirtyRead.setValue("郭涛", "123456");
			}
		});
		thread1.start();
		Thread.sleep(1000);
		dirtyRead.getValue();
	}

}
