package com.lyss.java.concurrent.threadcommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
/**
 * wait notify 方法
 * wait释放锁
 * notify不释放锁
 * 因为wait和notify方法定义在Object类中，因此会被所有的类所继承。
 * wait()方法使得当前线程必须要等待，等到另外一个线程调用notify()或者notifyAll()方法。
 * 当前的线程必须拥有当前对象的monitor，也即lock，就是锁。
 * 线程调用wait()方法，释放它对锁的拥有权，然后等待另外的线程来通知它（通知的方式是notify()或者notifyAll()方法），
 * 		这样它才能重新获得锁的拥有权和恢复执行。
 * 要确保调用wait()方法的时候拥有锁，即，wait()方法的调用必须放在synchronized方法或synchronized块中。
 * 一个小比较：
 * 当线程调用了wait()方法时，它会释放掉对象的锁。
 * 另一个会导致线程暂停的方法：Thread.sleep()，它会导致线程睡眠指定的毫秒数，
 * 		但线程在睡眠的过程中是不会释放掉对象的锁的。
 * 
 *
 *　　notify()方法会唤醒一个等待当前对象的锁的线程。
 *　如果多个线程在等待，它们中的一个将会选择被唤醒。这种选择是随意的，和具体实现有关。
 *		（线程等待一个对象的锁是由于调用了wait方法中的一个）。
 * 被唤醒的线程是不能被执行的，需要等到当前线程放弃这个对象的锁。
 * 被唤醒的线程将和其他线程以通常的方式进行竞争，来获得对象的锁。也就是说，被唤醒的线程并没有什么优先权，也没有什么劣势，
 * 		对象的下一个线程还是需要通过一般性的竞争。
 * notify()方法应该是被拥有对象的锁的线程所调用。
 *（This method should only be called by a thread that is the owner of this object's monitor.）
 * 换句话说，和wait()方法一样，notify方法调用必须放在synchronized方法或synchronized块中。
 * wait()和notify()方法要求在调用时线程已经获得了对象的锁，因此对这两个方法的调用需要放在synchronized方法或
 * 		synchronized块中。
 *　一个线程变为一个对象的锁的拥有者是通过下列三种方法：
 * 1.执行这个对象的synchronized实例方法。
 * 2.执行这个对象的synchronized语句块。这个语句块锁的是这个对象。
 * 3.对于Class类的对象，执行那个类的synchronized、static方法
 */
public class ListAdd2 {

	private volatile static List list = new ArrayList<>();
	public void add(){
		list.add("guotao");
	}
	public int size(){
		return list.size();
	}
	public static void main(String[] args) {
		final ListAdd2 list = new ListAdd2();
		//实例化出来一个锁
		//当时用wait 和notify方法时，一定要配置synchronized关键字使用
		//final Object lock = new Object();
		
		final CountDownLatch countDownLatch = new CountDownLatch(1);//可以实现实时通知效果  参数值代表通知次数
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//synchronized (lock) {
						for (int i = 0; i < 10; i++) {
							list.add();
							System.out.println("当前线程："+Thread.currentThread().getName()+"---添加了一个元素");
							Thread.sleep(500);
							if (list.size() == 5) {
								System.out.println("已发出通知。。");
								//lock.notify();
								countDownLatch.countDown();
							}
						}
					//}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				//synchronized (lock) {
					if (list.size() != 5) {
						try {
							//lock.wait();
							countDownLatch.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("当前线程："+Thread.currentThread().getName()+"---list size == 5  线程终止");
					throw new RuntimeException();
				}		
			//}
		},"t2");
		//t2先执行 将获得的锁释放，并等待唤醒
		t1.start();
		t2.start();
	}
}
/**
 * 使用wait和notify的弊端：
 * 		notify发出唤醒通知时不释放锁
 * 		直到执行完了以后才会释放锁
 * 
 * 使用CountdownLatch可以 实现实时通知并唤醒的效果
 */

