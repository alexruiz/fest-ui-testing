/*
 * Created on May 1, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.ui.testing.screenshot;

import static org.fest.ui.testing.screenshot.ImageFormats.PNG;
import static org.fest.util.Files.newFile;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * Saves an image as a file in the file system.
 *
 * @author Alex Ruiz
 */
class ImageFileWriter {

  static ImageFileWriter instance() {
    return LazyLoader.INSTANCE;
  }

  /**
   * Saves an image as a PNG file to the file system. If there is already a file with the same name, it will be deleted
   * and recreated.
   * @param image the {@code BufferedImage} to be saved.
   * @param filePath the path of the file to create.
   * @return {@code false} if the image could not be saved.
   * @exception IOException if an error occurs while saving the image.
   */
  boolean writeAsPng(BufferedImage image, String filePath) throws IOException {
    File file = newFile(filePath);
    if (file.isFile()) file.delete();
    return ImageIO.write(image, PNG, file);
  }

  private ImageFileWriter() {}
  
  private static class LazyLoader {
    private static ImageFileWriter INSTANCE = new ImageFileWriter();
  }
}
