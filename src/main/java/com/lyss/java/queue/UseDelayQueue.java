package com.lyss.java.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class UseDelayQueue {

	public static void main(String[] args) {
		DelayQueue<Delayed> queue = new DelayQueue<>();
		//queue.offer("郭涛", 2, TimeUnit.SECONDS);
	}
}
