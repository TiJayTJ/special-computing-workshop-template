package ru.spbu.apcyb.svp.tasks.task5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The WordCounter class provides methods for counting occurrences of words in a text file,
 * outputting the counts to a specified file, and generating output files with repeated words in a
 * specified directory.
 */
public class WordCounter {

  static Logger log = Logger.getLogger(WordCounter.class.getName());

  /**
   * Private constructor to prevent instantiation of the class.
   */
  private WordCounter() {
  }

  /**
   * Counts the occurrences of words in a text file and outputs the counts to a file. Additionally,
   * generates output files with repeated words in a specified directory.
   *
   * @param inputFile  The path to the input text file.
   * @param outputFile The path to the output file for word counts.
   * @param directory  The path to the directory for generating output files with repeated words.
   * @return A map containing words as keys and their occurrences as values.
   */
  public static Map<String, Long> wordCount(Path inputFile, Path outputFile, Path directory) {
    Map<String, Long> occurrencesAmount;
    try (Stream<String> lineStream = Files.lines(inputFile)) {
      occurrencesAmount = lineStream.flatMap(l -> Stream.of(l.split("[^a-zA-ZЁёА-я0-9]")))
          .map(String::toLowerCase)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
    occurrencesAmount.remove("");

    // skip reserved words
    List<String> reservedWords = Arrays.asList("con", "prn", "aux", "nul", "com1", "lpt1");
    for (String word : reservedWords) {
      if (occurrencesAmount.containsKey(word)) {
        log.log(Level.WARNING, "Reserved word {0} was found and skipped", word);
        occurrencesAmount.remove(word);
      }
    }

    outputCounts(occurrencesAmount, outputFile);
    outputWords(occurrencesAmount, directory);

    return occurrencesAmount;
  }

  /**
   * Outputs word counts to a specified file.
   *
   * @param map        A map containing words as keys and their occurrences as values.
   * @param outputFile The path to the output file for word counts.
   */
  public static void outputCounts(Map<String, Long> map, Path outputFile) {
    try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile.toFile()))) {
      for (Map.Entry<String, Long> entry : map.entrySet()) {
        fileWriter.write(entry.getKey() + ": " + entry.getValue().toString());
        fileWriter.newLine();
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Generates output files with repeated words in a specified directory.
   *
   * @param map       A map containing words as keys and their occurrences as values.
   * @param directory The path to the directory for generating output files with repeated words.
   */
  public static void outputWords(Map<String, Long> map, Path directory) {
    List<CompletableFuture<Void>> cfList = new ArrayList<>();
    map.forEach((word, count)
        -> cfList.add(CompletableFuture.runAsync(
            () -> outputOneWord(word, count, directory))));

    log.info("Waiting for CompletableFuture to finish");
    for (CompletableFuture<Void> cf : cfList) {
      while (!cf.isDone()) {
      }
    }
    log.info("CompletableFuture is finished");
  }

  /**
   * Outputs a file with repeated occurrences of a word.
   *
   * @param word      The word to be repeated.
   * @param count     The number of times the word should be repeated.
   * @param directory The path to the directory for generating output files with repeated words.
   */
  private static void outputOneWord(String word, long count, Path directory) {
    File file = new File(directory.toString(), word);
    try (BufferedWriter fileWriter = new BufferedWriter(
        new FileWriter(file))) {
      for (int i = 0; i < count; i++) {
        fileWriter.write(word + " ");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Checks whether the generated output files match the expected words.
   *
   * @param map       A map containing words as keys and their occurrences as values.
   * @param directory The path to the directory for checking output files.
   * @return true if the output files match the expected words, false otherwise.
   * @throws IOException If an error occurs during file listing.
   */
  public static boolean checkMap(Map<String, Long> map, Path directory) throws IOException {
    List<String> files;
    try (Stream<Path> fileStream = Files.list(directory)) {
      files = fileStream.map(file -> file.getFileName().toString()).toList();
    }
    for (Map.Entry<String, Long> entry : map.entrySet()) {
      String word = entry.getKey();
      if (!files.contains(word)) {
        return false;
      }
    }
    return true;
  }
}
