package ru.spbu.apcyb.svp.tasks.atm;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.IntStream;


/**
 * An automaton that calculates all possible options for exchanging this amount for banknotes.
 */
public class Atm {

  private final long amount;
  private final List<Long> denominations = new ArrayList<>();

  /**
   * Constructs ATM which takes information from input stream.
   *
   * @param inputStream input stream
   */
  public Atm(InputStream inputStream) {
    Scanner in = new Scanner(inputStream);
    String str;
    try {
      str = in.nextLine();
      this.amount = Long.parseLong(str);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("amount cannot be parsed to long");
    }
    try {
      str = in.nextLine();
      List<Long> input = Arrays.stream(str.split(" ")).map(Long::parseLong).distinct().toList();
      if (input.isEmpty()) {
        throw new IllegalArgumentException("empty string");
      }
      IntStream.range(0, input.size()).forEach(i -> this.denominations.add(input.get(i)));
      this.denominations.sort(Collections.reverseOrder());
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("denominations cannot be parsed to long");
    }
    checkPositivity();
  }

  /**
   * Constructs ATM based on list of denomination and target sum.
   *
   * @param denominations list of denominations
   * @param amount        target sum
   */
  public Atm(List<Long> denominations, long amount) {
    this.denominations.addAll(denominations);
    this.amount = amount;
    checkPositivity();
  }

  /**
   * checking for the positivity of the amount and denominations.
   */
  public void checkPositivity() {
    if (amount <= 0) {
      throw new IllegalArgumentException("the amount is not positive");
    }
    for (Long denomination : denominations) {
      if (denomination <= 0) {
        throw new IllegalArgumentException("there is not positive denomination");
      }
    }
  }

  /**
   * Iterative algorithm for calculating sum output combinations.
   *
   * @return a list of all combinations of issuing denominations
   */
  public List<List<Long>> iterativeSolution() {
    List<Long> combination = new ArrayList<>();
    List<List<Long>> result = new ArrayList<>();

    Deque<StackData> todoStack = new ArrayDeque<>();
    todoStack.push(new StackData(denominations, combination));

    while (!todoStack.isEmpty()) {

      StackData current = todoStack.pop();

      long s = current.intermediate.stream().mapToLong(i -> i).sum();
      if (s == amount) {
        result.add(current.intermediate);
      }
      if (s < amount) {
        for (int i = 0; i < current.denominations.size(); i++) {
          ArrayList<Long> remaining = new ArrayList<>();
          long n = current.denominations.get(i);
          for (int j = i; j < current.denominations.size(); j++) {
            remaining.add(current.denominations.get(j));
          }
          List<Long> partialRec = new ArrayList<>(current.intermediate);
          partialRec.add(n);

          todoStack.push(new StackData(remaining, partialRec));
        }
      }
    }
    return result;
  }

  /**
   * Logging all possible options for exchanging.
   */
  public void logResult(List<List<Long>> result) {
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

  private record StackData(List<Long> denominations, List<Long> intermediate) {

  }
}