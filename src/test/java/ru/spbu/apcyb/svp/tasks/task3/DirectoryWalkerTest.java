package ru.spbu.apcyb.svp.tasks.task3;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DirectoryWalkerTest {

  @Test
  public void inputTest() {
    Path directory = Path.of("src/main/resources/task3/test_directory");
    Path resultFile = Path.of("src/main/resources/task3/result");
    Assertions.assertDoesNotThrow(() -> new DirectoryWalker(directory, resultFile));
  }

  @Test
  public void wrongDirectoryTest() {
    Path directory = Path.of("src/main/resources/task3/test_directory777");
    Path resultFile = Path.of("src/main/resources/task3/result");
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class,
        () -> new DirectoryWalker(directory, resultFile));
    Assertions.assertEquals("Path must be a directory!", ex.getMessage());
  }

  @Test
  public void wrongFileTest() {
    Path directory = Path.of("src/main/resources/task3/test_directory");
    Path resultFile = Path.of("src/main/resources/task3");
    Exception ex = Assertions.assertThrows(IllegalArgumentException.class,
        () -> new DirectoryWalker(directory, resultFile));
    Assertions.assertEquals("Path must be a file!", ex.getMessage());
  }

  @Test
  public void scanTest() throws IOException {
    Path directory = Path.of("src/main/resources/task3/test_directory");
    Path resultFile = Path.of("src/main/resources/task3/result");
    Path expectedFile = Path.of("src/main/resources/task3/expected");
    DirectoryWalker walker = new DirectoryWalker(directory, resultFile);
    walker.scan();
    String expected = """
        test_directory
        empty1
        empty2
        empty3
        sub_directory
        empty4
        """;
    try (FileWriter writer = new FileWriter(expectedFile.toFile())) {
      writer.write(expected);
    } catch (IOException e) {
      throw new IOException("Возникла ошибка во время записи, проверьте данные.");
    }
    Assertions.assertEquals(-1, Files.mismatch(expectedFile, resultFile));
  }
}