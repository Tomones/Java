package com.lyss.java.concurrent.threadcommunication;

import java.util.ArrayList;
import java.util.List;

public class ListAdd1 {

	private volatile static List list = new ArrayList<>();
	public void add(){
		list.add("guotao");
	}
	public int size(){
		return list.size();
	}
	public static void main(String[] args) {
		final ListAdd1 listAdd1 = new ListAdd1();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 10; i++) {
						listAdd1.add();
						System.out.println("当前线程："+Thread.currentThread().getName()+"---添加了一个元素");
						Thread.sleep(500);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		},"t1").start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (listAdd1.size()==5) {
						System.out.println("当前线程："+Thread.currentThread().getName()+"---list size == 5  线程终止");
						throw new RuntimeException();
					}
				}		
			}
		},"t2").start();
	}
}
