package ru.spbu.apcyb.svp.tasks.atm;

/**
 * Launches atm with the ability to enter from the console
 */
public class Main {

  /**
   * Main method
   *
   * @param args args
   */
  public static void main(String[] args) {
    System.out.println("""
        Enter the input data separated by a space.
        Input format:
        First number is the amount to be exchanged
        The subsequent digits are the denominations of the bills
        (example 5 2 3)""");
    new Atm(System.in);
  }
}
