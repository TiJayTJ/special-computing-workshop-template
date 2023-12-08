package ru.spbu.apcyb.svp.tasks.task2.arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class MyArrayList<T> implements List<T> {

  private static final int DEFAULT_CAPACITY = 10;
  private Object[] elementData;
  private int size;
  private static final String UNSUPPORTED_OPERATION = "unsupported operation";


  public MyArrayList() {
    this.elementData = new Object[DEFAULT_CAPACITY];
    this.size = 0;
  }

  public MyArrayList(T[] inputArray) {
    this.size = inputArray.length;
    this.elementData = Arrays.copyOf(inputArray, this.size);
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    if (index < 0 || index >= size){
      throw new IndexOutOfBoundsException();
    }
    return (T) elementData[index];
  }

  @Override
  public boolean add(T t) {
    if (size >= elementData.length){
      boolean expandReturn = expand();
      if(!expandReturn){
        throw new IndexOutOfBoundsException("Cannot add element");
      }
    }
    elementData[size] = t;
    size += 1;

    return true;
  }

  private boolean expand(){
    if (elementData.length >= Integer.MAX_VALUE - 1){
      return false;
    }
    long capacityL = (this.elementData.length * 3L) / 2L + 1L;
    int capacity = (capacityL < Integer.MAX_VALUE - 1) ? (int)capacityL: Integer.MAX_VALUE - 1;
    elementData = Arrays.copyOf(elementData, capacity);
    return true;
  }

  @Override
  public void add(int index, T element) {
    if (index < 0 || index >= size){
      throw new IndexOutOfBoundsException();
    }
    if (size >= elementData.length){
      boolean expandReturn = expand();
      if(!expandReturn){
        throw new IndexOutOfBoundsException("Cannot add element");
      }
    }
    System.arraycopy(elementData, index, elementData, index+1, size - index);
    elementData[index] = element;
    size += 1;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof List)) {
      return false;
    }

    if (hashCode() != o.hashCode()){
      return false;
    }

    for (int i = 0; i < size; i++) {
      if (!Objects.equals(elementData[i], ((MyArrayList<T>) o).elementData[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return size;
  }

  public int size(){
    return size;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T remove(int index) {
    if (index < 0 || index >= size){
      throw new IndexOutOfBoundsException();
    }
    T removedElement = (T) elementData[index];
    System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
    size -= 1;
    return removedElement;
  }

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

  @Override
  public boolean isEmpty() {
    return size == 0;
  }


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
