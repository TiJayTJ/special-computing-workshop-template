package ru.spbu.apcyb.svp.tasks.atm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AtmTests {

  @Test
  public void simpleTest(){
    List<Long> denominations = new ArrayList<>(Arrays.asList(2L, 1L));
    int amount = 4;

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(2L, 2L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L)));

    Atm atm = new Atm(denominations, amount);
    Assertions.assertEquals(expected, atm.getResult());
  }

  @Test()
  public void incorrectInputTest(){
    InputStream in = new ByteArrayInputStream(("fsf").getBytes());
    Exception ex = Assertions.assertThrows(NumberFormatException.class, () -> new Atm(in));
    Assertions.assertEquals("string can't be parsed to long", ex.getMessage());
  }

  @Test
  public void example1Test(){
    InputStream in = new ByteArrayInputStream(("5\n3 2").getBytes());
    Atm atm = new Atm(in);

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(3L, 2L)));

    Assertions.assertEquals(expected, atm.getResult());
  }

  @Test
  public void example2Test(){
    InputStream in = new ByteArrayInputStream(("4\n2 1").getBytes());
    Atm atm = new Atm(in);

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(2L, 2L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L)));

    Assertions.assertEquals(expected, atm.getResult());
  }

  @Test
  public void example3Test(){
    InputStream in = new ByteArrayInputStream(("4\n1 2").getBytes());
    Atm atm = new Atm(in);

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(2L, 2L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L)));

    Assertions.assertEquals(expected, atm.getResult());
  }
  @Test
  public void example4Test(){
    InputStream in = new ByteArrayInputStream(("1000\n1").getBytes());
    Atm atm = new Atm(in);

    List<Long> trueResult = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      trueResult.add(1L);
    }
    List<List<Long>> expected = new ArrayList<>();
    expected.add(trueResult);

    Assertions.assertEquals(expected, atm.getResult());
  }

  @Test
  public void example5Test(){
    InputStream in = new ByteArrayInputStream(("1000\n500 1").getBytes());
    Atm atm = new Atm(in);

    List<Long> trueResult1 = new ArrayList<>();
    trueResult1.add(500L);
    trueResult1.add(500L);
    List<Long> trueResult2 = new ArrayList<>();
    trueResult2.add(500L);
    for (int i = 0; i < 500; i++) {
      trueResult2.add(1L);
    }
    List<Long> trueResult3 = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      trueResult3.add(1L);
    }

    List<List<Long>> expected = new ArrayList<>();
    expected.add(trueResult1);
    expected.add(trueResult2);
    expected.add(trueResult3);

    Assertions.assertEquals(expected, atm.getResult());
  }

  @Test
  public void testEmptyAmount(){
    InputStream in = new ByteArrayInputStream(("\n5").getBytes());
    Atm atm = new Atm(in);
    // TODO: 28.11.2023
  }
}
