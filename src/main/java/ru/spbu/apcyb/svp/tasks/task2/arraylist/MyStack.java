package ru.spbu.apcyb.svp.tasks.task2.arraylist;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * The MyStack class is an implementation of a generic stack using an underlying
 * MyArrayList to store elements. It extends the functionality of the java.util.Stack class.
 * This class provides methods for pushing, popping, peeking, checking if the stack is empty,
 * and handling equality and hash code comparison.
 *
 * @param <T> The type of elements stored in the stack.
 */
public class MyStack<T> extends Stack<T> {

  private final transient MyArrayList<T> elementStack;

  /**
   * Constructs an empty MyStack with an underlying MyArrayList.
   */
  public MyStack() {
    elementStack = new MyArrayList<>();
  }

  /**
   * Constructs a MyStack containing the elements of the specified array,
   * in the order they are returned by the array's iterator.
   *
   * @param inputArray The array whose elements are to be placed into the stack.
   */
  public MyStack(T[] inputArray) {
    elementStack = new MyArrayList<>(inputArray);
  }

  /**
   * Pushes an item onto the top of the stack.
   *
   * @param element The element to be pushed onto the stack.
   * @return The element argument.
   */
  @Override
  public T push(T element) {
    elementStack.add(element);
    return element;
  }

  /**
   * Removes the element at the top of the stack and returns it.
   *
   * @return The element at the top of the stack.
   * @throws EmptyStackException If the stack is empty.
   */
  @Override
  public synchronized T pop() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return elementStack.remove(elementStack.size() - 1);
  }

  /**
   * Looks at the element at the top of the stack without removing it.
   *
   * @return The element at the top of the stack.
   * @throws EmptyStackException If the stack is empty.
   */
  @Override
  public synchronized T peek() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return elementStack.get(elementStack.size() - 1);
  }

  /**
   * Tests if the stack is empty.
   *
   * @return true if the stack is empty, false otherwise.
   */
  @Override
  public boolean empty() {
    return elementStack.size() == 0;
  }

  /**
   * Compares this MyStack with the specified object for equality.
   * Returns true if and only if the specified object is also a MyStack,
   * both stacks have the same size, and all corresponding pairs of elements
   * in the two stacks are equal. This method overrides the equals method
   * in the java.util.Stack class.
   *
   * @param o The object to be compared for equality with this MyStack.
   * @return true if the specified object is equal to this MyStack, false otherwise.
   */
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

  /**
   * Returns the hash code value for this MyStack. This method overrides
   * the hashCode method in the java.util.Stack class.
   *
   * @return The hash code value for this MyStack.
   */
  @Override
  public synchronized int hashCode() {
    return elementStack.hashCode();
  }
}
