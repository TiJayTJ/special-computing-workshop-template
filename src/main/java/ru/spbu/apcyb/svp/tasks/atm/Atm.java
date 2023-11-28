package ru.spbu.apcyb.svp.tasks.atm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * wef.
 */
public class Atm {

  private long amount;
  private List<Long> denominations = new ArrayList<>();
  private final List<List<Long>> result = new ArrayList<>();

  /**
   * wefliihweg.
   *
   * @param inputStream iwjeg
   * @throws NumberFormatException oihwrgih
   */
  public Atm(InputStream inputStream) throws NumberFormatException {
    fillFields(inputStream);
    checkPositivity();
    recursiveSolution(denominations, amount, new ArrayList<>());
    logResult();
  }

  private void fillFields(InputStream inputStream) {
    Scanner in = new Scanner(inputStream);
    try {
      String str = in.nextLine();
      amount = Long.parseLong(str);
      str = in.nextLine();
      List<Long> input = Arrays.stream(str.split(" ")).map(Long::parseLong).distinct().toList();
      if (input.isEmpty()) {
        throw new IllegalArgumentException("empty string");
      }
      IntStream.range(0, input.size()).forEach(i -> denominations.add(input.get(i)));
      denominations.sort(Collections.reverseOrder());
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("string can't be parsed to long");
    }
  }

  /**
   * lhwrg.
   *
   * @param denominations ihwrg
   * @param amount        klkhrg
   */
  public Atm(List<Long> denominations, long amount) {
    this.denominations = denominations;
    this.amount = amount;
    checkPositivity();
    recursiveSolution(denominations, amount, new ArrayList<>());
    logResult();
  }

  private void checkPositivity() {
    if (amount < 0) {
      throw new IllegalArgumentException("the amount is negative");
    }
    for (Long denomination : denominations) {
      if (denomination <= 0) {
        throw new IllegalArgumentException("there is not positive denomination");
      }
    }
  }

  private void recursiveSolution(List<Long> denominations, long amount, List<Long> combinations) {
    long s = combinations.stream().mapToLong(i -> i).sum();

    if (s == amount) {
      result.add(combinations);
    }
    if (s >= amount) {
      return;
    }
    for (int i = 0; i < denominations.size(); i++) {
      ArrayList<Long> remaining = new ArrayList<>();
      long n = denominations.get(i);
      for (int j = i; j < denominations.size(); j++) {
        remaining.add(denominations.get(j));
      }
      List<Long> partialRec = new ArrayList<>(combinations);
      partialRec.add(n);

      recursiveSolution(remaining, amount, partialRec);
    }
  }

  /**
   * kjhqef.
   */
  public void logResult() {
    Logger logger = Logger.getLogger(getClass().getName());
    StringBuilder bld = new StringBuilder();

    for (List<Long> res : result) {
      for (Long numb : res) {
        bld.append(numb);
        bld.append(" ");
      }
      bld.append("\n");
    }
    String str = bld.toString();
    logger.info(str);
  }

  public long getAmount() {
    return amount;
  }

  public List<Long> getDenominations() {
    return denominations;
  }

  public List<List<Long>> getResult() {
    return result;
  }
}