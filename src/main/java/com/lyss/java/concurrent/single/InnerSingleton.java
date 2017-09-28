package com.lyss.java.concurrent.single;

public class InnerSingleton {

	//在一个静态类中实例化一个静态的对象
	private static class Singleton{
		private static Singleton singleton = new Singleton();
	}
	//提供一个对外结构返回一个对象
	public static Singleton getInstance() {
		return Singleton.singleton;
	}
}
