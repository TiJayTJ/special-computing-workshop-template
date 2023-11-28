package ru.spbu.apcyb.svp.tasks.atm;

/**
 * ouihqewf.
 */
public class Main {

  /**
   * ihqef.
   *
   * @param args oihw
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
