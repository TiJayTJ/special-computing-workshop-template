package ru.spbu.apcyb.svp.tasks.task2.arraylist;

import java.util.EmptyStackException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MyStackTest {

  @Test
  public void pushTest() {
    MyStack<Object> myStack = new MyStack<>(new Object[]{3, ' '});
    myStack.push(6L);
    MyStack<Object> expected = new MyStack<>(new Object[]{3, ' ', 6L});

    Assertions.assertEquals(expected, myStack);
  }

  @Test
  public void popTest() {
    MyStack<Object> myStack = new MyStack<>();
    myStack.push(3);
    myStack.push(' ');
    MyStack<Object> expected = new MyStack<>();
    expected.push(3);
    Assertions.assertEquals(' ', myStack.pop());
    Assertions.assertEquals(expected, myStack);
  }

  @Test
  public void popEmptyTest() {
    MyStack<Object> myStack = new MyStack<>(new Object[]{});
    Assertions.assertThrows(EmptyStackException.class, myStack::pop);
  }

  @Test
  public void peekTest() {
    MyStack<Object> myStack = new MyStack<>(new Object[]{3, ' '});
    Assertions.assertEquals(' ', myStack.peek());
  }

  @Test
  public void peekEmptyTest() {
    MyStack<Object> myStack = new MyStack<>(new Object[]{});
    Assertions.assertThrows(EmptyStackException.class, myStack::peek);
  }

  @Test
  public void emptyTest() {
    MyStack<Object> myStack = new MyStack<>(new Object[]{3});
    MyStack<Object> myStackEmpty = new MyStack<>(new Object[]{});
    Assertions.assertFalse(myStack.empty());
    Assertions.assertTrue(myStackEmpty.empty());
  }
}