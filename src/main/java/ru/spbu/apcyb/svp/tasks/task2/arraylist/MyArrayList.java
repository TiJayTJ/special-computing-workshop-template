package ru.spbu.apcyb.svp.tasks.task2.arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * The MyArrayList class is an implementation of a dynamic array that implements
 * the List interface. It supports generic elements and provides basic operations
 * such as adding, getting, and removing elements. This implementation also includes
 * an expansion mechanism to increase the capacity of the array dynamically.
 *
 * @param <T> The type of elements stored in the list.
 */
public class MyArrayList<T> implements List<T> {

  private static final int DEFAULT_CAPACITY = 10;

  private Object[] elementData;

  private int size;

  private static final String UNSUPPORTED_OPERATION = "unsupported operation";

  /**
   * Constructs an empty MyArrayList with the default initial capacity.
   */
  public MyArrayList() {
    this.elementData = new Object[DEFAULT_CAPACITY];
    this.size = 0;
  }

  /**
   * Constructs a MyArrayList containing the elements of the specified array.
   *
   * @param inputArray The array whose elements are to be placed into this list.
   */
  public MyArrayList(T[] inputArray) {
    this.size = inputArray.length;
    this.elementData = Arrays.copyOf(inputArray, this.size);
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index The index of the element to be retrieved.
   * @return The element at the specified position in this list.
   * @throws IndexOutOfBoundsException If the index is out of range (index < 0 || index >= size).
   */
  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return (T) elementData[index];
  }

  /**
   * Appends the specified element to the end of this list.
   *
   * @param t The element to be added to this list.
   * @return true (as specified by Collection.add)
   */
  @Override
  public boolean add(T t) {
    if (size >= elementData.length) {
      boolean expandReturn = expand();
      if (!expandReturn) {
        throw new IndexOutOfBoundsException("Cannot add element");
      }
    }
    elementData[size] = t;
    size += 1;

    return true;
  }

  /**
   * Expands the capacity of the array when it is full.
   *
   * @return true if the expansion is successful, false otherwise.
   */
  private boolean expand() {
    if (elementData.length >= Integer.MAX_VALUE - 1) {
      return false;
    }
    long capacityL = (this.elementData.length * 3L) / 2L + 1L;
    int capacity = (capacityL < Integer.MAX_VALUE - 1) ? (int) capacityL : Integer.MAX_VALUE - 1;
    elementData = Arrays.copyOf(elementData, capacity);
    return true;
  }

  /**
   * Inserts the specified element at the specified position in this list.
   *
   * @param index   The index at which the specified element is to be inserted.
   * @param element The element to be inserted.
   * @throws IndexOutOfBoundsException If the index is out of range (index < 0 || index >= size).
   */
  @Override
  public void add(int index, T element) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    if (size >= elementData.length) {
      boolean expandReturn = expand();
      if (!expandReturn) {
        throw new IndexOutOfBoundsException("Cannot add element");
      }
    }
    System.arraycopy(elementData, index, elementData, index + 1, size - index);
    elementData[index] = element;
    size += 1;
  }

  /**
   * Compares this MyArrayList with the specified object for equality.
   * Returns true if and only if the specified object is also a List,
   * both lists have the same size, and all corresponding pairs of
   * elements in the two lists are equal.
   *
   * @param o The object to be compared for equality with this MyArrayList.
   * @return true if the specified object is equal to this MyArrayList, false otherwise.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof List)) {
      return false;
    }

    if (hashCode() != o.hashCode()) {
      return false;
    }

    for (int i = 0; i < size; i++) {
      if (!Objects.equals(elementData[i], ((MyArrayList<T>) o).elementData[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the hash code value for this MyArrayList.
   *
   * @return The hash code value for this MyArrayList.
   */
  @Override
  public int hashCode() {
    return size;
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return The number of elements in this list.
   */
  public int size() {
    return size;
  }

  /**
   * Removes the element at the specified position in this list.
   *
   * @param index The index of the element to be removed.
   * @return The element that was removed from the list.
   * @throws IndexOutOfBoundsException If the index is out of range (index < 0 || index >= size).
   */
  @Override
  @SuppressWarnings("unchecked")
  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    T removedElement = (T) elementData[index];
    System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
    size -= 1;
    return removedElement;
  }

  /**
   * Returns true if this list contains the specified element.
   *
   * @param o The object to be checked for presence in this list.
   * @return true if this list contains the specified element, false otherwise.
   */
  @Override
  public boolean contains(Object o) {
    if (o == null) {
      for (int i = 0; i < size; i++) {
        if (elementData[i] == null) {
          return true;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (o.equals(elementData[i])) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns true if this list contains no elements.
   *
   * @return true if this list contains no elements, false otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  // Unsupported Operations

  @Override
  public T set(int index, T element) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public Iterator<T> iterator() {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public ListIterator<T> listIterator() {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
  }
}

