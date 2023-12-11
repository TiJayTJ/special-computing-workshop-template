package ru.spbu.apcyb.svp.tasks.task5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class WordCounterTest {

  @Test
  public void wordCountSmallTest() throws IOException {
    Path inputFile = Path.of("src/main/resources/Task5/text_small");
    Path outputFile = Path.of("src/main/resources/Task5/output_small.txt");
    Path directory = Path.of("src/main/resources/Task5/words_small");
    Path expectedFile = Path.of("src/main/resources/Task5/expected_small");
    Map<String, Long> map = WordCounter.wordCount(inputFile, outputFile, directory);

    Assertions.assertEquals(-1, Files.mismatch(expectedFile, outputFile));
    Assertions.assertTrue(WordCounter.checkMap(map, directory));
  }

  @Test
  public void wordCountLargeTest() throws IOException {
    Path inputFile = Path.of("src/main/resources/Task5/text_large.txt");
    Path outputFile = Path.of("src/main/resources/Task5/output_large.txt");
    Path directory = Path.of("src/main/resources/Task5/words_large");
    Map<String, Long> map = WordCounter.wordCount(inputFile, outputFile, directory);

    Assertions.assertTrue(WordCounter.checkMap(map, directory));
  }
}