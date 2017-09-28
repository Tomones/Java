并发队列：
	在并发队列上JDK提供了两套实现：一个是一ConcurrentLinkedQueue为代表的高性能队列（非阻塞），一个是已BlockingQueue为代表的阻塞队列，无论哪种都继承Queue。
	
	ConcurrentLinkedQueue：
		是一个适用于高并发场景下的队列，通过无锁的方式，实现了高并发状态下的高性能，通常ConcurrentLinkedQueue性能好于BlockingQueue。它是一个基于链接节点的无界线程安全队列。该队列的元素遵循先进先出原则，头是最先加入的尾是最近加入的，该队列不允许null元素。
	ConcurrentLinkedQueue的重要方法：
		add()和offer()都是加入元素的方法，在ConcurrentLinkedQueue中，这两个方法没有任何区别
		poll()和peek()都是取头元素节点，区别在于前者会删除元素，后者不会
		
	BlockingQueue接口:
		ArrayBlockingQueue:
			基于数组的阻塞队列实现，在ArrayBlockingQueue内部，维护了一个定长数组，以便缓存队列中的数据对象，其内部没有实现读写分离，也就意味着生产和消费不能完全并行，长度是需要定义的，可以指定先进先出，或先进后出，也叫有界队列，在很多场合适合使用
		LinkedBlockingQueue:
			基于链表的阻塞队列，同ArrayBlockingQueue类似，其内部也维持着一个数据缓存队列（该队列由一个链表构成），LinkedBlockingQueue之所以能够高效的处理并发数据，是因为其内部采用了分离锁（读写分离两个锁），从而实现生产者和消费者操作的完全并行，它是一个无界队列(不初始化长度)。
		PriorityBlockingQueue：
			基于优先级的阻塞队列（优先级的判断通过构造方法传入的Compator对象来决定，也就是说传入队列的对象必须实现Comparable接口），在实现PriorityBlockingQueue时，内部控制线程同步的锁采用的是公平锁，他也是一个无界队列。
		DelayQueue：
			带有延迟时间的Queue，其中的元素只有当指定的延迟时间到了，才能够从队列中获取到该元素，DelayQueue中的元素必须实现Delayed接口，DelayQueue是一个没有大小限制的队列，应用的场景很多，比如对缓存超时的数据进行移除，任务超时处理，空闲连接的关闭等等。
		SynchronousQueue：
			一种没有缓存的队列，生产者生产的数据会被消费者直接获取并消费。