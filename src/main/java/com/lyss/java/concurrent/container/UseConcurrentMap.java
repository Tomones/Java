package com.lyss.java.concurrent.container;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 用法和传统的HashMap一样
 * @author Administrator
 *
 */
public class UseConcurrentMap {

	public static void main(String[] args) {
		ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap<>();
		concurrentHashMap.put("key1", "aa");
		concurrentHashMap.put("key2", "bb");
		concurrentHashMap.put("key3", "cc");
		concurrentHashMap.putIfAbsent("key4", "dd");
		for(Map.Entry<String, Object> mp :concurrentHashMap.entrySet()){
			System.out.println("Key:"+mp.getKey()+"------value:"+mp.getValue());
		}
	}
}
