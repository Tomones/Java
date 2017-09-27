package com.lyss.java.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

//RandomAccess 用来表明其支持快速（通常是固定时间）随机访问
//Cloneable可以克隆对象
//Serializable 对象序列化就是把一个对象变为二进制的数据流的一种方法，通过对象序列化可以方便地实现对象的传输和存储
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

  // protected transient int modCount 从父类AbstractList继承而来 已从结构上修改 此列表的次数。从结构上修改是指更改列表的大小，或者打乱列表，
  // 从而使正在进行的迭代产生错误的结果。
  // 此字段由 iterator 和 listIterator 方法返回的迭代器和列表迭代器实现使用。
  // 如果意外更改了此字段中的值，则迭代器（或列表迭代器）将抛出 ConcurrentModificationException 来响应
  // next、remove、previous、set 或 add 操作。在迭代期间面临并发修改时，它提供了快速失败 行为，而不是非确定性行为。
  private static final long serialVersionUID = 8683452581122892189L;

  /**
   * transient的作用 一个对象只要实现了Serilizable接口，这个对象就可以被序列化，
   * java的这种序列化模式为开发者提供了很多便利，可以不必关系具体序列化的过程，
   * 只要这个类实现了Serilizable接口，这个的所有属性和方法都会自动序列化。 但是有种情况是有些属性是不需要序列号的，所以就用到这个关键字。
   * 只需要实现Serilizable接口， 将不需要序列化的属性前添加关键字transient，序列化对象的时候，
   * 这个属性就不会序列化到指定的目的地中。 The capacity of the ArrayList is the length of this
   * array buffer.
   */
  private transient Object[] elementData; // Object[]类型的数组，保存了添加到ArrayList中的元素。ArrayList的容量是该Object[]类型数组的长度

  /**
   * The size of the ArrayList (the number of elements it contains).
   *
   * @serial
   */
  private int size;// 数组实际长度的大小 小于elementData.length

  /**
   * 构造一个具有指定初始容量的空列表。 参数：initialCapacity - 列表的初始容量
   */
  public ArrayList(int initialCapacity) {
      super();
      if (initialCapacity < 0)
          throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
      this.elementData = new Object[initialCapacity];
  }

  /**
   * 构造一个初始容量为 10 的空列表
   */
  public ArrayList() {
      this(10);
  }

  /**
   * 构造一个包含指定 collection 的元素的列表
   */
  public ArrayList(Collection<? extends E> c) {
      elementData = c.toArray();
      size = elementData.length;
      // c.toArray 可能不是返回一个 Object[]
      if (elementData.getClass() != Object[].class)
          elementData = Arrays.copyOf(elementData, size, Object[].class);
  }

  /**
   * 将底层数组的容量调整为当前实际元素的大小，来释放空间
   */
  public void trimToSize() {
      modCount++;
      //当前数组的容量
      int oldCapacity = elementData.length;
       // 如果当前实际元素大小 小于 当前数组的容量，则进行缩容
      if (size < oldCapacity) {
          elementData = Arrays.copyOf(elementData, size);
      }
  }

  /**
   * 拓容 
   * 如有必要，增加此 ArrayList 实例的容量，以确保它至少能够容纳最小容量参数所指定的元素数。
   * 
   * @param minCapacity
   *            minCapacity - 所需的最小容量
   */
  public void ensureCapacity(int minCapacity) {
      if (minCapacity > 0)
          ensureCapacityInternal(minCapacity);
  }
  //数组容量检查，不够时则进行扩容
  private void ensureCapacityInternal(int minCapacity) {
      modCount++;
      // overflow-conscious code
      // 最小需要的容量大于当前数组的长度则进行扩容
      if (minCapacity - elementData.length > 0)
          grow(minCapacity);
  }

  /**
   * The maximum size of array to allocate. Some VMs reserve some header words
   * in an array. Attempts to allocate larger arrays may result in
   * OutOfMemoryError: Requested array size exceeds VM limit
   */
  private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

  /**
   * 数组拓容  
   * minCapacity 最小需要的容量
   */
  private void grow(int minCapacity) {
      // overflow-conscious code
      //当前数组的容量
      int oldCapacity = elementData.length;
      //拓容数组容量
      int newCapacity = oldCapacity + (oldCapacity >> 1);
     // 如果新扩容的数组长度还是比最小需要的容量小，则以最小需要的容量为长度进行扩容
      if (newCapacity - minCapacity < 0)
          newCapacity = minCapacity;
      //如果最小需要的容量比数组定义好的最大长度还大,则进行紧急拓容
      if (newCapacity - MAX_ARRAY_SIZE > 0)
          newCapacity = hugeCapacity(minCapacity);
      // minCapacity is usually close to size, so this is a win:
        // 进行数据拷贝，Arrays.copyOf底层实现是System.arrayCopy()
      elementData = Arrays.copyOf(elementData, newCapacity);
  }
  //紧急拓容 直接把数组容量拓展到Integer.MAX_VALUE
  private static int hugeCapacity(int minCapacity) {
      if (minCapacity < 0) // overflow
          throw new OutOfMemoryError();
      return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return the number of elements in this list
   */
  public int size() {
      return size;
  }

  /**
   * Returns <tt>true</tt> if this list contains no elements.
   *
   * @return <tt>true</tt> if this list contains no elements
   */
  public boolean isEmpty() {
      return size == 0;
  }

  // 如果此列表中包含指定的元素，则返回 true。
  public boolean contains(Object o) {
      return indexOf(o) >= 0;
  }

  // 返回此列表中首次出现的指定元素的索引，或如果此列表不包含元素，则返回 -1
  public int indexOf(Object o) {
      if (o == null) {
          for (int i = 0; i < size; i++)
              if (elementData[i] == null)
                  return i;
      } else {
          for (int i = 0; i < size; i++)
              if (o.equals(elementData[i]))
                  return i;
      }
      return -1;
  }

  // 返回此列表中最后一次出现的指定元素的索引，或如果此列表不包含索引，则返回 -1
  public int lastIndexOf(Object o) {
      if (o == null) {
          for (int i = size - 1; i >= 0; i--)
              if (elementData[i] == null)
                  return i;
      } else {
          for (int i = size - 1; i >= 0; i--)
              if (o.equals(elementData[i]))
                  return i;
      }
      return -1;
  }

  /**
   * 返回此 ArrayList 实例的浅表副本
   */
  public Object clone() {
      try {
          @SuppressWarnings("unchecked")
          ArrayList<E> v = (ArrayList<E>) super.clone();
          v.elementData = Arrays.copyOf(elementData, size);
          v.modCount = 0;
          return v;
      } catch (CloneNotSupportedException e) {
          // this shouldn't happen, since we are Cloneable
          throw new InternalError();
      }
  }

  /**
   * 返回包含此列表中所有元素的数组
   */
  public Object[] toArray() {
      return Arrays.copyOf(elementData, size);
  }

  /**
   * 返回包含此列表中所有元素的数组；返回数组的运行时类型是指定数组的运行时类型
   */
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] a) {
      if (a.length < size)
          // Make a new array of a's runtime type, but my contents:
          return (T[]) Arrays.copyOf(elementData, size, a.getClass());
      System.arraycopy(elementData, 0, a, 0, size);
      if (a.length > size)// 数组的长度比列表的元素数量多
          a[size] = null;// 多余的用null填充
      return a;
  }

  // Positional Access Operations取出elementData数组的下标为index的element

  @SuppressWarnings("unchecked")
  //返回index所在位置的元素的函数
  E elementData(int index) {
      return (E) elementData[index];
  }

  /**
   * 返回此列表中指定位置上的元素
   */
  public E get(int index) {
      //数组越界检查
      rangeCheck(index);
      //返回index所在位置的元素
      return elementData(index);
  }

  /**
   * 用指定的元素替代此列表中指定位置上的元素。
   */
  public E set(int index, E element) {
      //数组越界检查
      rangeCheck(index);
      //  取出要更新位置的元素，供返回使用
      E oldValue = elementData(index);
      // 将e赋值到该位置
      elementData[index] = element;
      //返回旧元素
      return oldValue;
  }

  /**
   * 将指定的元素添加到此列表的尾部。
   */
  public boolean add(E e) {
      // 数组拓容
      ensureCapacityInternal(size + 1); // Increments modCount!!
      // 将e放到列表尾部 容量加1
      elementData[size++] = e;
      return true;
  }

  /**
   * 将指定的元素插入此列表中的指定位置。
   */
  public void add(int index, E element) {
      //// 判断索引是否越界。
      rangeCheckForAdd(index);
      // 数组拓容
      ensureCapacityInternal(size + 1); // Increments modCount!!
      // 对数组进行复制处理，目的就是空出index的位置插入element，并将index后的元素位移一个位置
      System.arraycopy(elementData, index, elementData, index + 1, size - index);
      // 将指定的index位置赋值为element
      elementData[index] = element;
      // 容量加1
      size++;
  }

  /**
   * 移除此列表中指定位置上的元素。向左移动所有后续元素（将其索引减 1）。
   */
  public E remove(int index) {
      // 数组越界检查
      rangeCheck(index);
      //修改次数加1
      modCount++;
      // 取出要删除位置的元素，供返回使用
      E oldValue = elementData(index);
      //  计算数组要复制的数量
      int numMoved = size - index - 1;
      if (numMoved > 0)
           // 数组复制，就是将index之后的元素往前移动一个位置
          System.arraycopy(elementData, index + 1, elementData, index, numMoved);
       // 将数组最后一个元素置空
       // 因为删除了一个元素，然后index后面的元素都向前移动了，所以最后一个就没用了
      elementData[--size] = null; // size减一  让gc尽快回收 Let gc do its work
      return oldValue;
  }

  /**
   * 移除此列表中首次出现的指定元素（如果存在）。如果列表不包含此元素，则列表不做改动
   */
  public boolean remove(Object o) {
      // 对要删除的元素进行null判断
      // 对数据元素进行遍历查找，直到找到第一个要删除的元素，删除后进行返回，
      //如果要删除的元素正好是最后一个 ，时间复杂度可达O(n)
      if (o == null) {
          for (int index = 0; index < size; index++)
              //null值不能用equals比较  要用==比较
              if (elementData[index] == null) {
                  fastRemove(index);
                  return true;
              }
      } else {
          for (int index = 0; index < size; index++)
              //比较对象是否相等
              if (o.equals(elementData[index])) {
                  //快速删除 skips bounds checking and does not return the value removed.
                  fastRemove(index);
                  return true;
              }
      }
      return false;
  }

  /*
   * Private remove method that skips bounds checking and does not return the
   * value removed.
   */
  private void fastRemove(int index) {
      modCount++;
      int numMoved = size - index - 1;
      if (numMoved > 0)
          //进行数组复制，将index后的元素向前移动一个位置 
          System.arraycopy(elementData, index + 1, elementData, index, numMoved);
      elementData[--size] = null; // Let gc do its work
  }

  /**
   * 移除此列表中的所有元素。此调用返回后，列表将为空。
   */
  public void clear() {
      //修改次数加1
      modCount++;
      // 遍历赋空  让垃圾回收机制回收 Let gc do its work
      for (int i = 0; i < size; i++)
          elementData[i] = null;
      //size变为0
      size = 0;
  }

  /**增加一个集合元素
   * 将该 collection 中的所有元素添加到此列表的尾部
   */
  public boolean addAll(Collection<? extends E> c) {
      //将集合c转化为Object[]
      Object[] a = c.toArray();
      //得到c集合的元素的数量
      int numNew = a.length;
      //数组拓容
      ensureCapacityInternal(size + numNew); // Increments modCount
      //数组复制
      System.arraycopy(a, 0, elementData, size, numNew);
      //更新当前容器大小 ->容量变为size+numNew（添加的集合的元素的数量）
      size += numNew;
      return numNew != 0;
  }

  /**
   * 从指定的位置开始，增加一个集合的元素
   * 
   */
  public boolean addAll(int index, Collection<? extends E> c) {
      // 判断索引是否越界。
      rangeCheckForAdd(index);

      Object[] a = c.toArray();
      int numNew = a.length;
      //数组拓容
      ensureCapacityInternal(size + numNew); // Increments modCount
       // 计算需要移动的长度
      int numMoved = size - index;
      if (numMoved > 0)
          // 数组复制，空出第index到index+numNum的位置，即将数组index后的元素向右移动numNum个位置
          System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
      // 将要插入的集合元素复制到数组空出的位置中
      System.arraycopy(a, 0, elementData, index, numNew);
      size += numNew;
      return numNew != 0;
  }

  /**
   * 移除列表中索引在 fromIndex（包括）和 toIndex（不包括）之间的所有元素
   */
  protected void removeRange(int fromIndex, int toIndex) {
      //修改次数加1
      modCount++;
      //复制长度
      int numMoved = size - toIndex;
      //数组复制  原理就是移动  根之前的add ,remove一样理解
      System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
      // Let gc do its work
      //新容量
      int newSize = size - (toIndex - fromIndex);
      //移除位置(后面一截的 比如移除了2-3的位置的 ，则4-5的元素就会前移，所以后面的不需要了)的全部赋空 让Gc回收
      while (size != newSize)
          elementData[--size] = null;
  }

  /**
   * 数组越界检查
   */
  private void rangeCheck(int index) {
      if (index >= size)
          throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
  }

  /**
   * 数组越界检查 针对 add and addAll.
   */
  private void rangeCheckForAdd(int index) {
      if (index > size || index < 0)
          throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
  }

  /**
   *越界错误信息.
   */
  private String outOfBoundsMsg(int index) {
      return "Index: " + index + ", Size: " + size;
  }

  /**
   * Removes from this list all of its elements that are contained in the
   * specified collection.
   *
   * @param c
   *            collection containing elements to be removed from this list
   * @return {@code true} if this list changed as a result of the call
   * @throws ClassCastException
   *             if the class of an element of this list is incompatible with
   *             the specified collection (
   *             <a href="Collection.html#optional-restrictions">optional</a>)
   * @throws NullPointerException
   *             if this list contains a null element and the specified
   *             collection does not permit null elements (
   *             <a href="Collection.html#optional-restrictions">optional</a>
   *             ), or if the specified collection is null
   * @see Collection#contains(Object)
   */
  public boolean removeAll(Collection<?> c) {
      return batchRemove(c, false);
  }

  /**
   * 移除包含c集合中的所有元素
   */
  public boolean retainAll(Collection<?> c) {
      return batchRemove(c, true);
  }

  private boolean batchRemove(Collection<?> c, boolean complement) {
      final Object[] elementData = this.elementData;
      int r = 0, w = 0;
      boolean modified = false;
      try {
          for (; r < size; r++)
              if (c.contains(elementData[r]) == complement)
                  elementData[w++] = elementData[r];
      } finally {
          // Preserve behavioral compatibility with AbstractCollection,
          // even if c.contains() throws.
          if (r != size) {
              System.arraycopy(elementData, r, elementData, w, size - r);
              w += size - r;
          }
          if (w != size) {
              for (int i = w; i < size; i++)
                  elementData[i] = null;
              modCount += size - w;
              size = w;
              modified = true;
          }
      }
      return modified;
  }

  /**
   * elementData数组是使用 transient 修饰的，关于 transient 关键字的作用简单说就是 java自带默认机制进行序列化
   * 的时候，被其修饰的属性不需要维持。
   * 如果elementData不需要维持，那么怎么进行反序列化，又怎么保证序列化和反序列化数据的正确性？
   * 难道不需要存储？用大腿想一下那当然是不可以的嘛，既然需要存储，它是怎么实现的呢？ 
   * ArrayList一定是使用了自定义的序列化方式，到底是不是这样的呢？看下面两个方法： elementData
   * 是一个数据存储数组，而数组是定长的，它会初始化一个容量， 等容量不足时再扩充容量（扩容方式为数据拷贝，后面会详细解释）， 再通俗一点说就是比如
   */
  private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
      // Write out element count, and any hidden stuff
      int expectedModCount = modCount;
      s.defaultWriteObject();

      // Write out array length
      s.writeInt(elementData.length);

      // Write out all elements in the proper order.
      for (int i = 0; i < size; i++)
          s.writeObject(elementData[i]);

      if (modCount != expectedModCount) {
          throw new ConcurrentModificationException();
      }

  }

  /**
   * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
   * deserialize it).
   */
  private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
      // Read in size, and any hidden stuff
      s.defaultReadObject();

      // Read in array length and allocate array
      int arrayLength = s.readInt();
      Object[] a = elementData = new Object[arrayLength];

      // Read in all elements in the proper order.
      for (int i = 0; i < size; i++)
          a[i] = s.readObject();
  }

  /**
   * Returns a list iterator over the elements in this list (in proper
   * sequence), starting at the specified position in the list. The specified
   * index indicates the first element that would be returned by an initial
   * call to {@link ListIterator#next next}. An initial call to
   * {@link ListIterator#previous previous} would return the element with the
   * specified index minus one.
   *
   * <p>
   * The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
   *
   * @throws IndexOutOfBoundsException
   *             {@inheritDoc}
   */
  public ListIterator<E> listIterator(int index) {
      if (index < 0 || index > size)
          throw new IndexOutOfBoundsException("Index: " + index);
      return new ListItr(index);
  }

  /**
   * Returns a list iterator over the elements in this list (in proper
   * sequence).
   *
   * <p>
   * The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
   *
   * @see #listIterator(int)
   */
  public ListIterator<E> listIterator() {
      return new ListItr(0);
  }

  /**
   * Returns an iterator over the elements in this list in proper sequence.
   *
   * <p>
   * The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
   *
   * @return an iterator over the elements in this list in proper sequence
   */
  public Iterator<E> iterator() {
      return new Itr();
  }

  /**
   * An optimized version of AbstractList.Itr
   */
  private class Itr implements Iterator<E> {
      int cursor; // index of next element to return
      int lastRet = -1; // index of last element returned; -1 if no such
      int expectedModCount = modCount;

      public boolean hasNext() {
          return cursor != size;
      }

      @SuppressWarnings("unchecked")
      public E next() {
          checkForComodification();
          int i = cursor;
          if (i >= size)
              throw new NoSuchElementException();
          Object[] elementData = ArrayList.this.elementData;
          if (i >= elementData.length)
              throw new ConcurrentModificationException();
          cursor = i + 1;
          return (E) elementData[lastRet = i];
      }

      public void remove() {
          if (lastRet < 0)
              throw new IllegalStateException();
          checkForComodification();

          try {
              ArrayList.this.remove(lastRet);
              cursor = lastRet;
              lastRet = -1;
              expectedModCount = modCount;
          } catch (IndexOutOfBoundsException ex) {
              throw new ConcurrentModificationException();
          }
      }

      final void checkForComodification() {
          if (modCount != expectedModCount)
              throw new ConcurrentModificationException();
      }
  }

  /**
   * An optimized version of AbstractList.ListItr
   */
  private class ListItr extends Itr implements ListIterator<E> {
      ListItr(int index) {
          super();
          cursor = index;
      }

      public boolean hasPrevious() {
          return cursor != 0;
      }

      public int nextIndex() {
          return cursor;
      }

      public int previousIndex() {
          return cursor - 1;
      }

      @SuppressWarnings("unchecked")
      public E previous() {
          checkForComodification();
          int i = cursor - 1;
          if (i < 0)
              throw new NoSuchElementException();
          Object[] elementData = ArrayList.this.elementData;
          if (i >= elementData.length)
              throw new ConcurrentModificationException();
          cursor = i;
          return (E) elementData[lastRet = i];
      }

      public void set(E e) {
          if (lastRet < 0)
              throw new IllegalStateException();
          checkForComodification();

          try {
              ArrayList.this.set(lastRet, e);
          } catch (IndexOutOfBoundsException ex) {
              throw new ConcurrentModificationException();
          }
      }

      public void add(E e) {
          checkForComodification();

          try {
              int i = cursor;
              ArrayList.this.add(i, e);
              cursor = i + 1;
              lastRet = -1;
              expectedModCount = modCount;
          } catch (IndexOutOfBoundsException ex) {
              throw new ConcurrentModificationException();
          }
      }
  }

  //截取 fromIndex到 fromIndex之间的元素
  public List<E> subList(int fromIndex, int  fromIndex) {
      //位置检查
      subListRangeCheck(fromIndex, toIndex, size);
      //内部类 继承父类并实现了方法
      return new SubList(this, 0, fromIndex, toIndex);
  }
  //位置检查
  static void subListRangeCheck(int fromIndex, int toIndex, int size) {
      if (fromIndex < 0)
          throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
      if (toIndex > size)
          throw new IndexOutOfBoundsException("toIndex = " + toIndex);
      if (fromIndex > toIndex)
          throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
  }

  private class SubList extends AbstractList<E> implements RandomAccess {
      private final AbstractList<E> parent;
      private final int parentOffset;
      private final int offset;
      int size;

      SubList(AbstractList<E> parent, int offset, int fromIndex, int toIndex) {
          this.parent = parent;
          this.parentOffset = fromIndex;
          this.offset = offset + fromIndex;
          this.size = toIndex - fromIndex;
          this.modCount = ArrayList.this.modCount;
      }

      public E set(int index, E e) {
          rangeCheck(index);
          checkForComodification();
          E oldValue = ArrayList.this.elementData(offset + index);
          ArrayList.this.elementData[offset + index] = e;
          return oldValue;
      }

      public E get(int index) {
          rangeCheck(index);
          checkForComodification();
          return ArrayList.this.elementData(offset + index);
      }

      public int size() {
          checkForComodification();
          return this.size;
      }

      public void add(int index, E e) {
          rangeCheckForAdd(index);
          checkForComodification();
          parent.add(parentOffset + index, e);
          this.modCount = parent.modCount;
          this.size++;
      }

      public E remove(int index) {
          rangeCheck(index);
          checkForComodification();
          E result = parent.remove(parentOffset + index);
          this.modCount = parent.modCount;
          this.size--;
          return result;
      }

      protected void removeRange(int fromIndex, int toIndex) {
          checkForComodification();
          parent.removeRange(parentOffset + fromIndex, parentOffset + toIndex);
          this.modCount = parent.modCount;
          this.size -= toIndex - fromIndex;
      }

      public boolean addAll(Collection<? extends E> c) {
          return addAll(this.size, c);
      }

      public boolean addAll(int index, Collection<? extends E> c) {
          rangeCheckForAdd(index);
          int cSize = c.size();
          if (cSize == 0)
              return false;

          checkForComodification();
          parent.addAll(parentOffset + index, c);
          this.modCount = parent.modCount;
          this.size += cSize;
          return true;
      }

      public Iterator<E> iterator() {
          return listIterator();
      }

      public ListIterator<E> listIterator(final int index) {
          checkForComodification();
          rangeCheckForAdd(index);
          final int offset = this.offset;

          return new ListIterator<E>() {
              int cursor = index;
              int lastRet = -1;
              int expectedModCount = ArrayList.this.modCount;

              public boolean hasNext() {
                  return cursor != SubList.this.size;
              }

              @SuppressWarnings("unchecked")
              public E next() {
                  checkForComodification();
                  int i = cursor;
                  if (i >= SubList.this.size)
                      throw new NoSuchElementException();
                  Object[] elementData = ArrayList.this.elementData;
                  if (offset + i >= elementData.length)
                      throw new ConcurrentModificationException();
                  cursor = i + 1;
                  return (E) elementData[offset + (lastRet = i)];
              }

              public boolean hasPrevious() {
                  return cursor != 0;
              }

              @SuppressWarnings("unchecked")
              public E previous() {
                  checkForComodification();
                  int i = cursor - 1;
                  if (i < 0)
                      throw new NoSuchElementException();
                  Object[] elementData = ArrayList.this.elementData;
                  if (offset + i >= elementData.length)
                      throw new ConcurrentModificationException();
                  cursor = i;
                  return (E) elementData[offset + (lastRet = i)];
              }

              public int nextIndex() {
                  return cursor;
              }

              public int previousIndex() {
                  return cursor - 1;
              }

              public void remove() {
                  if (lastRet < 0)
                      throw new IllegalStateException();
                  checkForComodification();

                  try {
                      SubList.this.remove(lastRet);
                      cursor = lastRet;
                      lastRet = -1;
                      expectedModCount = ArrayList.this.modCount;
                  } catch (IndexOutOfBoundsException ex) {
                      throw new ConcurrentModificationException();
                  }
              }

              public void set(E e) {
                  if (lastRet < 0)
                      throw new IllegalStateException();
                  checkForComodification();

                  try {
                      ArrayList.this.set(offset + lastRet, e);
                  } catch (IndexOutOfBoundsException ex) {
                      throw new ConcurrentModificationException();
                  }
              }

              public void add(E e) {
                  checkForComodification();

                  try {
                      int i = cursor;
                      SubList.this.add(i, e);
                      cursor = i + 1;
                      lastRet = -1;
                      expectedModCount = ArrayList.this.modCount;
                  } catch (IndexOutOfBoundsException ex) {
                      throw new ConcurrentModificationException();
                  }
              }

              final void checkForComodification() {
                  if (expectedModCount != ArrayList.this.modCount)
                      throw new ConcurrentModificationException();
              }
          };
      }

      public List<E> subList(int fromIndex, int toIndex) {
          subListRangeCheck(fromIndex, toIndex, size);
          return new SubList(this, offset, fromIndex, toIndex);
      }

      private void rangeCheck(int index) {
          if (index < 0 || index >= this.size)
              throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
      }

      private void rangeCheckForAdd(int index) {
          if (index < 0 || index > this.size)
              throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
      }

      private String outOfBoundsMsg(int index) {
          return "Index: " + index + ", Size: " + this.size;
      }

      private void checkForComodification() {
          if (ArrayList.this.modCount != this.modCount)
              throw new ConcurrentModificationException();
      }
  }
}