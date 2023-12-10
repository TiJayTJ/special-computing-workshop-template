package ru.spbu.apcyb.svp.tasks.task4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The TangentCalculator class provides methods for calculating the tangent of numbers using both
 * single-threaded and multithreaded approaches. It also includes methods for reading input from a
 * file, writing output to a file, and generating random numbers.
 */
public class TangentCalculator {

  /**
   * Private constructor to prevent instantiation of the class.
   */
  private TangentCalculator() {
  }

  /**
   * Calculates the tangent of each element in the list using a single thread.
   *
   * @param numbers The list of numbers to calculate the tangent for.
   * @return The list of tangent values.
   */
  public static List<Double> calculateSingleThread(List<Double> numbers) {
    return numbers.stream().map(Math::tan).toList();
  }

  /**
   * Calculates the tangent of each element in the list using multiple threads.
   *
   * @param numbers The list of numbers to calculate the tangent for.
   * @return The list of tangent values.
   * @throws RuntimeException If an ExecutionException or InterruptedException occurs during
   *                          calculation.
   */
  public static List<Double> calculateMultithreaded(List<Double> numbers) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    try {
      Future<List<Double>> futuresList = executorService.submit(
          () -> numbers.parallelStream().map(Math::tan).toList());
      return futuresList.get();

    } catch (ExecutionException | InterruptedException ex) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(ex.getMessage());
    } finally {
      executorService.shutdown();
    }
  }

  /**
   * Reads a list of numbers from a specified input file.
   *
   * @param inputFile The path to the input file.
   * @return The list of numbers read from the input file.
   * @throws RuntimeException If an IOException occurs during file reading.
   */
  public static List<Double> input(Path inputFile) {
    if (Files.isDirectory(inputFile)) {
      throw new IllegalArgumentException("Path must be a file!");
    }
    List<Double> numbers = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
      String line = reader.readLine();
      while (line != null) {
        String[] lineSplit = line.split(" ");
        for (String numb : lineSplit) {
          numbers.add(Double.parseDouble(numb));
        }
        line = reader.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return numbers;
  }

  /**
   * Writes a list of numbers to a specified output file.
   *
   * @param outputFile The path to the output file.
   * @param numbers    The list of numbers to be written to the output file.
   * @throws RuntimeException If an IOException occurs during file writing.
   */
  public static void output(Path outputFile, List<Double> numbers) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile()))) {
      for (Double listElem : numbers) {
        writer.write(listElem.toString());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException("An error occurred during writing, please check the data.");
    }
  }

  /**
   * Generates a list of random numbers and writes them to a specified output file.
   *
   * @param outputFile The path to the output file.
   * @param range      The number of random numbers to generate.
   */
  public static void numbersGenerator(Path outputFile, int range) {
    List<Double> numbers = new ArrayList<>();
    for (int i = 0; i < range; i++) {
      numbers.add(Math.random() * range * 2 - range);
    }
    output(outputFile, numbers);
  }
}

