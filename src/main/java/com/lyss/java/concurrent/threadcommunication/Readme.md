线程通信：
	线程是操作系统中独立的个体，但这些个体如果不经过特殊的处理就不能成为一个
	整体，线程间的通信就成为整体必用方式之一，当线程存在通信指挥，系统间的交
	互性会更强大，在提高CPU使用率的同时还会使开发人员对线程任务在处理过程中
	进行有效的把控和监督
	
	使用wait/notify 方法实现线程间的通信。（注意这两个方法都是object的类
	的方法，换句话说Java为所有的对象提供了这两个方法）
	1.wait和notify必须配合synchroized关键字使用
	2.wait方法释放锁，notify方法不释放锁