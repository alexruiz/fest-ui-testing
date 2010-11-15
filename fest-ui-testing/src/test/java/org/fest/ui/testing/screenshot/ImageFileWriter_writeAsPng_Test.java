/*
 * Created on Nov 10, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.ui.testing.screenshot;

import static java.awt.Color.BLUE;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.util.UUID.randomUUID;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.util.Files.temporaryFolderPath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.*;

/**
 * Tests for <code>{@link ImageFileWriter#writeAsPng(BufferedImage, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageFileWriter_writeAsPng_Test {

  private BufferedImage image;
  private String path;
  private ImageFileWriter writer;

  @Before public void setUp() {
    image = newImage(5, 5, BLUE);
    path = temporaryFolderPath() + randomUUID() + ".png";
    writer = ImageFileWriter.instance();
  }

  @After public void tearDown() {
    File imageFile = new File(path);
    if (imageFile.isFile()) imageFile.delete();
  }

  @Test public void should_save_image_to_file() throws IOException {
    boolean wasSaved = writer.writeAsPng(image, path);
    assertThat(wasSaved).isTrue();
    BufferedImage savedImage = read(path);
    assertThat(savedImage).isEqualTo(image);
  }

  private static BufferedImage newImage(int width, int height, Color color) {
    BufferedImage image = new BufferedImage(width, height, TYPE_INT_ARGB);
    Graphics graphics = image.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, width, height);
    return image;
  }
}
