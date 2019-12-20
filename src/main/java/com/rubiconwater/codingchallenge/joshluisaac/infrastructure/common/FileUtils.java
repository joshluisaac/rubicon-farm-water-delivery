package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

  public static void flushToDisk(String jsonValue, File file) throws IOException {
    try (OutputStream outputStream = new FileOutputStream(file, false)) {
      outputStream.write(jsonValue.getBytes());
      outputStream.flush();
    }
  }
}
