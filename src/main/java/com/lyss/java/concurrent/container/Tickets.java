package com.lyss.java.concurrent.container;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Tickets {

	public static void main(String[] args) {
		//初始化车票并添加车票：
				//避免线程同步可使用Vector代替ArrayList  HashTable代替HashMap
		final Vector<String> tickets = new Vector<String>();
		
		//被Collections.synchronizedMap 包裹的HashMap 是线程安全的
		//Map<Object, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
		
		
		for(int i = 0; i < 1000; i++){
			tickets.add("火车票"+i);
		}
		
		for(int j = 0; j< 10 ;j++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						if (tickets.isEmpty()) break;
						System.out.println("当前线程："+Thread.currentThread().getName()+"===="+tickets.remove(0));
					}
				}
			},"t"+j).start();
		}
	}
}
