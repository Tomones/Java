package com.lyss.java.util;
/**
 * 手写ArrayList
 * @author Administrator
 *
 */
public class MyArrayList {
	//创建一个空数组对象
	private static final Object[] EMPTY_ELEMENTDATA = {};
	//申明一个临时的数组
	transient Object[] elementData;
	//默认的初始化长度为10
	private static final int DEFAULT_CAPACITY = 10;
	//集合的长度
	private int size;
	
	
	//初始化一个空集合，并制定初始化容量
	public MyArrayList(int initialCapacity){
		//如果初始化长度大于0，则创建一个对象数组
		if (initialCapacity>0) {
			this.elementData = new Object[initialCapacity];
		}else {
			//否则置空
			this.elementData =EMPTY_ELEMENTDATA;
			throw new IllegalArgumentException("集合的初始化长度为"+initialCapacity);
		}
	}
	//默认初始长度是10
	public MyArrayList(){
		this(10);
	}
	 //定义判断长度方法size()
	public int size(){
		return size;
	}
	 //判断是否非空
	public boolean isEmpty(){
		return size == 0;
	}
	//判断数组是否越界
	public void rangeCheck(int index){
		if (index<0 || index >=size) {
			throw new IndexOutOfBoundsException("数组越界异常，下标为："+index);
		}
	}
	//扩展长度
	private void ensureCapacity(){
		if (size>=elementData.length) {
			Object[] temp = new Object[elementData.length*3/2];
			System.arraycopy(elementData, 0, temp, 0, size);
			elementData = temp;
		}
	}
	/**
	 * 增删改查
	 */
	public void add(Object object){
		ensureCapacity();
		elementData[size++]=object;
	}
	public void add(int index,Object object){
		rangeCheck(index);
		ensureCapacity();
		System.arraycopy(elementData, index, elementData, index+1, size-index);
		elementData[index] = object;
		size++;
	}
	public Object get(int index) {
		rangeCheck(index);
		return elementData[index];
		}

		public Object set(int index, Object obj) {
		rangeCheck(index);
		Object oldValue = elementData[index];
		elementData[index] = obj;
		return oldValue;
		}

		public void remove(int index) {
		rangeCheck(index);
		ensureCapacity();
		System.arraycopy(elementData, index+1, elementData, index, size-index-1);
		size--;
		}
		
		public static void main(String[] args) {
			MyArrayList list = new MyArrayList();
			list.add("asdsad");
			System.out.println(list.get(0));
		}
}
