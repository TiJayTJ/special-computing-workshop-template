package ru.spbu.apcyb.svp.tasks.task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TangentCalculatorTest {

  @Test
  public void inputTest() {
    Path fileName = Path.of("src/main/resources/task4/test_input_numbers");
    List<Double> result = TangentCalculator.input(fileName);
    List<Double> expected = Arrays.asList(6.0, 4.0, 2.0, 5.0, 7.0, 23.0);

    Assertions.assertEquals(expected, result);
  }

  @Test
  public void outputTest() throws IOException {
    Path fileName = Path.of("src/main/resources/task4/output_numbers");
    Path expectedFile = Path.of("src/main/resources/task4/test_input_numbers");
    List<Double> numbers = Arrays.asList(6.0, 4.0, 2.0, 5.0, 7.0, 23.0);
    TangentCalculator.output(fileName, numbers);

    Assertions.assertEquals(-1, Files.mismatch(expectedFile, fileName));
  }

  @Test
  public void calculateSingleThread() throws IOException {
    Path inputFile = Path.of("src/main/resources/task4/numbers_1");
    Path outputFile = Path.of("src/main/resources/task4/output_numbers");
    Path expectedFile = Path.of("src/main/resources/task4/expected_1");

    List<Double> numbers = TangentCalculator.input(inputFile);
    List<Double> numbersTangent = TangentCalculator.calculateSingleThread(numbers);
    TangentCalculator.output(outputFile, numbersTangent);

    Assertions.assertEquals(-1, Files.mismatch(expectedFile, outputFile));
  }

  @Test
  public void calculateMultithreaded() throws IOException {
    Path inputFile = Path.of("src/main/resources/task4/numbers_1000000");
    Path outputFile = Path.of("src/main/resources/task4/output_numbers");
    Path expectedFile = Path.of("src/main/resources/task4/expected_1000000");

    List<Double> numbers = TangentCalculator.input(inputFile);
    List<Double> numbersTangent = TangentCalculator.calculateMultithreaded(numbers);
    TangentCalculator.output(outputFile, numbersTangent);

    Assertions.assertEquals(-1, Files.mismatch(expectedFile, outputFile));
  }

  @Test
  public void compare() {
    int[] dataSize = {1, 100, 1000000};
    for (int j : dataSize) {
      System.out.println("Data size: " + j);

      List<Double> numbers = TangentCalculator.input(
          Path.of("src/main/resources/task4/numbers_" + j));
      long start = System.nanoTime();
      TangentCalculator.calculateSingleThread(numbers);
      long endSingle = System.nanoTime();
      TangentCalculator.calculateMultithreaded(numbers);
      long endMulti = System.nanoTime();

      double oneThreadTime = (endSingle - start) / 1000000000f;
      double tenThreadTime = (endMulti - endSingle) / 1000000000f;

      System.out.println("The calculation time is 1 thread: " + (oneThreadTime));
      System.out.println("The calculation time is 10 thread: " + (tenThreadTime));
      System.out.println("Difference: " + Math.abs(tenThreadTime - oneThreadTime));
      System.out.println("-----------------------------------------------------");
    }

    Assertions.assertTrue(true);
  }
}