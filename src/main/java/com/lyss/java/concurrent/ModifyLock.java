package com.lyss.java.concurrent;
/**
 * 同一对象属性的修改不会影响锁的情况
 */
public class ModifyLock {
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

	public synchronized void changeAttributte(String name,int age){
		try {
			System.out.println("当前线程："+Thread.currentThread().getName()+"开始");
			this.setName(name);
			this.setAge(age);
			System.out.println("当前线程："+Thread.currentThread().getName()+"修改对象内容为："+this.getName()+"=="+this.getAge());
			Thread.sleep(2000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"结束");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void main(String[] args) {
		final ModifyLock stringLock = new ModifyLock();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				stringLock.changeAttributte("郭涛", 18);
			}
		},"t1").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				stringLock.changeAttributte("LY", 18);
			}
		},"t2").start();
		
	}
}
