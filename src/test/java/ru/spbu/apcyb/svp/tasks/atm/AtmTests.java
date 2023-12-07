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
  public void simpleTest() {
    List<Long> denominations = new ArrayList<>(Arrays.asList(2L, 1L));
    int amount = 4;

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 2L)));

    Atm atm = new Atm(denominations, amount);
    List<List<Long>> result = atm.iterativeSolution();
    atm.logResult(result);
    Assertions.assertEquals(expected, result);
  }

  @Test
  public void example1Test() {
    InputStream in = new ByteArrayInputStream(("5\n3 2").getBytes());
    Atm atm = new Atm(in);

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(3L, 2L)));

    Assertions.assertEquals(expected, atm.iterativeSolution());
  }

  @Test
  public void example2Test() {
    InputStream in = new ByteArrayInputStream(("4\n2 1").getBytes());
    Atm atm = new Atm(in);

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 2L)));

    Assertions.assertEquals(expected, atm.iterativeSolution());
  }

  @Test
  public void example3Test() {
    InputStream in = new ByteArrayInputStream(("4\n1 2").getBytes());
    Atm atm = new Atm(in);

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 1L, 1L)));
    expected.add(new ArrayList<>(Arrays.asList(2L, 2L)));

    Assertions.assertEquals(expected, atm.iterativeSolution());
  }

  @Test
  public void example4Test() {
    InputStream in = new ByteArrayInputStream(("1000\n1").getBytes());
    Atm atm = new Atm(in);

    List<Long> trueResult = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      trueResult.add(1L);
    }
    List<List<Long>> expected = new ArrayList<>();
    expected.add(trueResult);

    Assertions.assertEquals(expected, atm.iterativeSolution());
  }

  @Test
  public void example5Test() {
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
    expected.add(trueResult3);
    expected.add(trueResult2);
    expected.add(trueResult1);

    Assertions.assertEquals(expected, atm.iterativeSolution());
  }

  @Test
  public void incorrectInputTest() {
    InputStream in = new ByteArrayInputStream(("fsf").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("amount cannot be parsed to long", ex.getMessage());
  }

  @Test
  public void emptyAmountTest() {
    InputStream in = new ByteArrayInputStream(("\n2 1").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("amount cannot be parsed to long", ex.getMessage());
  }

  @Test
  public void emptyDenominationsTest() {
    InputStream in = new ByteArrayInputStream(("5\n\n").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("denominations cannot be parsed to long", ex.getMessage());
  }

  @Test
  public void spaceInputTest() {
    InputStream in = new ByteArrayInputStream(("5\n  \n").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("empty string", ex.getMessage());
  }

  @Test
  public void negativeAmountTest() {
    InputStream in = new ByteArrayInputStream(("-5\n2 1").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("the amount is not positive", ex.getMessage());
  }

  @Test
  public void negativeDenominationsTest() {
    InputStream in = new ByteArrayInputStream(("5\n-1 1").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("there is not positive denomination", ex.getMessage());
  }

  @Test
  public void zeroAmountTest() {
    InputStream in = new ByteArrayInputStream(("0\n2 1").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("the amount is not positive", ex.getMessage());
  }

  @Test
  public void zeroDenominationTest() {
    InputStream in = new ByteArrayInputStream(("5\n0").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("there is not positive denomination", ex.getMessage());
  }

  @Test()
  public void formulaInputTest() {
    InputStream in = new ByteArrayInputStream(("5\n1+1 3").getBytes());
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Atm(in));
    Assertions.assertEquals("denominations cannot be parsed to long", ex.getMessage());
  }

  @Test()
  public void duplicateDenominationsTest() {
    InputStream in = new ByteArrayInputStream(("5\n1 1").getBytes());

    List<List<Long>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(1L, 1L, 1L, 1L, 1L)));

    Atm atm = new Atm(in);
    Assertions.assertEquals(expected, atm.iterativeSolution());
  }
}
