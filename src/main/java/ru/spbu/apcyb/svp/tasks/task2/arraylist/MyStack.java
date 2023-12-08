package ru.spbu.apcyb.svp.tasks.task2.arraylist;

import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> extends Stack<T> {

  private final transient MyArrayList<T> elementStack;

  public MyStack() {
    elementStack = new MyArrayList<>();
  }

  public MyStack(T[] inputArray) {
    elementStack = new MyArrayList<>(inputArray);
  }

  @Override
  public T push(T element) {
    elementStack.add(element);
    return element;
  }

  @Override
  public synchronized T pop() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return elementStack.remove(elementStack.size() - 1);
  }

  @Override
  public synchronized T peek() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return elementStack.get(elementStack.size() - 1);
  }

  @Override
  public boolean empty() {
    return elementStack.size() == 0;
  }

  @Override
  public synchronized boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (getClass() != o.getClass()) {
      return false;
    }
    if (hashCode() != o.hashCode()) {
      return false;
    }

    return elementStack.equals(((MyStack<?>) o).elementStack);
  }

  @Override
  public synchronized int hashCode() {
    return elementStack.hashCode();
  }
}
