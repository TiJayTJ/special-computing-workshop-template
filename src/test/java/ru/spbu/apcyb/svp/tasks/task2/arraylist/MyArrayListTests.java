package ru.spbu.apcyb.svp.tasks.task2.arraylist;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MyArrayListTests {

  @Test
  public void constructorTest() {
    MyArrayList<Object> list = new MyArrayList<>(new Object[]{1, ' ', 3L});
    Assertions.assertEquals(1, list.get(0));
    Assertions.assertEquals(' ', list.get(1));
    Assertions.assertEquals(3L, list.get(2));

    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-2));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
  }

  @Test
  public void addTest() {
    MyArrayList<Object> list = new MyArrayList<>(new Object[]{});
    list.add(1);
    list.add(' ');
    list.add(3L);
    MyArrayList<Object> expected = new MyArrayList<>(new Object[]{1, ' ', 3L});

    Assertions.assertEquals(expected, list);
  }

  @Test
  public void addByIndexTest() {
    MyArrayList<Object> list = new MyArrayList<>(new Object[]{1, 2, 3});
    list.add(1, 4);
    MyArrayList<Object> expected = new MyArrayList<>(new Object[]{1, 4, 2, 3});

    Assertions.assertEquals(expected, list);
  }

  @Test
  public void remove() {
    MyArrayList<Object> list = new MyArrayList<>(new Object[]{1, 4, 2, 3});
    list.remove(0);
    MyArrayList<Object> expected = new MyArrayList<>(new Object[]{4, 2, 3});

    Assertions.assertEquals(expected, list);
  }

  @Test
  public void containsTrue() {
    MyArrayList<Object> list = new MyArrayList<>(new Object[]{1, null, 3L});

    Assertions.assertTrue(list.contains(null));
  }

  @Test
  public void containsFalse() {
    MyArrayList<Object> list = new MyArrayList<>(new Object[]{1, ' ', 3L});

    Assertions.assertFalse(list.contains(" f"));
  }
}