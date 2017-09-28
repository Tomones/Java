package com.lyss.java.queue;

import java.util.concurrent.PriorityBlockingQueue;

public class UsePriorityBlockingQueue {

	public static void main(String[] args) {
		
		PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
		Task t1 = new Task();
		t1.setId(4);
		t1.setName("Task1");
		Task t2 = new Task();
		t2.setId(1);
		t2.setName("Task2");
		Task t3 = new Task();
		t3.setId(6);
		t3.setName("Task3");
		queue.add(t1);
		queue.add(t2);
		queue.add(t3);
		for (Task task : queue) {
			System.out.println(task.getId()+"===="+task.getName());
		}
		
	}
}
