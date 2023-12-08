package ru.spbu.apcyb.svp.tasks.task3;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * The DirectoryWalker class is responsible for scanning a given directory and
 * writing the list of file names in that directory to a specified file.
 */
public class DirectoryWalker {

  private final Path directory;

  private final Path file;

  /**
   * Constructs a new DirectoryWalker object with the specified directory and file paths.
   *
   * @param directory The path of the directory to be scanned.
   * @param file      The path of the file where the list of file names will be written.
   * @throws IllegalArgumentException If the provided directory is not a directory or
   *                                  if the provided file is a directory.
   */
  public DirectoryWalker(Path directory, Path file) {
    if (!Files.isDirectory(directory)) {
      throw new IllegalArgumentException("Path must be a directory!");
    }
    if (Files.isDirectory(file)) {
      throw new IllegalArgumentException("Path must be a file!");
    }

    this.directory = directory;
    this.file = file;
  }

  /**
   * Scans the specified directory and writes the list of file names to the specified file.
   *
   * @throws IOException If an I/O error occurs during the scanning or writing process.
   */
  public void scan() throws IOException {
    List<String> list;
    try (Stream<Path> walk = Files.walk(directory)) {
      list = walk.map(f -> f.getFileName().toString()).toList();
    }

    StringBuilder sb = new StringBuilder();

    for (String fileName : list) {
      sb.append(fileName);
      sb.append("\n");
    }

    String result = sb.toString();

    try (FileWriter writer = new FileWriter(file.toFile())) {
      writer.write(result);
    } catch (IOException e) {
      throw new IOException("An error occurred during writing, please check the data.");
    }
  }
}
