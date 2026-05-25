package com.example.filehandling;

import java.io.*;
import java.nio.file.Files;

public class FileHandlingEx {
  String filePath = "C:\\Users\\rahgangw1\\code\\mytext.txt";
  String destFilePath = "C:\\Users\\rahgangw1\\code\\myNewText.txt";

  public static void main(String[] args) {
    new FileHandlingEx().doSomething();
  }

  private void doSomething() {
    writeFile();
    readFile();
    copyFile();
  }

  private void writeFile() {
    File file = new File(filePath);
    if (!file.exists()) {
      boolean isFileCreated = false;
      try {
        isFileCreated = file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("File created : " + isFileCreated);
    }

    try (FileWriter fileWriter = new FileWriter(file, true); ) {
      fileWriter.write("\nMy new text123");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readFile() {
    File file = new File(filePath);
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); ) {
      String data = "";
      while (data != null) {
        data = bufferedReader.readLine();
        System.out.println(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void copyFile() {
    File file = new File(filePath);
    File destinationFile = new File(destFilePath);
    if (!destinationFile.exists()) {
      try {
        Files.copy(file.toPath(), destinationFile.toPath());
        System.out.println("File copied");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
