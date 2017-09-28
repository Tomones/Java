package com.lyss.java.concurrent.container;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseCopyOnWrite {

	public static void main(String[] args) {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
		//ReentrantLock 重入锁
		list.add("aa");
		
		ReentrantLock reentrantLock = new  ReentrantLock(); //重入锁(性能高)
		ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//读写锁（读写分离）
	}
}
