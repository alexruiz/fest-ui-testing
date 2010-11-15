/*
 * Created on Nov 9, 2010
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

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.util.logging.Level.WARNING;
import static org.fest.ui.testing.screenshot.ImageFormats.PNG;
import static org.fest.util.Strings.*;

import java.awt.image.BufferedImage;
import java.util.logging.*;

import org.fest.util.VisibleForTesting;

/**
 * Takes a screenshot of the desktop.
 *
 * @author Alex Ruiz
 */
public class DesktopCamera {

  /**
   * Returns the singleton instance of this class.
   * @return the singleton instance of this class.
   */
  public static DesktopCamera instance() {
    return LazyLoader.INSTANCE;
  }

  private static class LazyLoader {
    private static final DesktopCamera INSTANCE = newDesktopCamera();
  }

  private static DesktopCamera newDesktopCamera() {
    DesktopCamera camera = null;
    try {
      Displays displays = new Displays(RobotFactory.instance(), getLocalGraphicsEnvironment());
      camera = new DesktopCamera(ImageFileWriter.instance(), displays);
    } catch (Throwable t) {
      logger.log(Level.WARNING, "Unable to create a DesktopCamera", t);
    }
    return camera;
  }
  
  private static Logger logger = Logger.getAnonymousLogger();

  private final ImageFileWriter writer;
  private final Displays displays;

  @VisibleForTesting DesktopCamera(ImageFileWriter writer, Displays displays) {
    this.writer = writer;
    this.displays = displays;
  }

  /**
   * Takes a screenshot of the desktop and saves it as a PNG file.
   * <p>
   * In the case of multiple displays, this method will take a screenshot per display, and it will save them as one
   * image file per screenshot. Each image will have the same name (specified by {@code path}) with a different index.
   * For example, let's assume we have 2 displays and we pass the path "/tmp/myImage." This method will store the 
   * screenshots of both displays as "/tmp/myImage0.png" and "/tmp/myImage1.png," for the first and second display 
   * respectively.
   * </p>
   * @param path the path of the file to save the screenshot to. It must not include the extension ".png" since this
   * method may add an index to {@code path} if the system has multiple displays.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   */
  public void saveDesktopAsPng(String path) {
    validate(path);
    try {
      BufferedImage[] screenshots = displays.desktopScreenshots();
      int screenshotCount = screenshots.length;
      if (screenshotCount == 1) {
        writer.writeAsPng(screenshots[0], join(path, PNG).with("."));
        return;
      }
      for (int i = 0; i < screenshotCount; i++)
        writer.writeAsPng(screenshots[i], concat(path, Integer.toString(i), PNG));
    } catch (Throwable t) {
      logger.log(WARNING, "Unable to take screenshot(s) of the desktop", t);
    }
  }
  
  private void validate(String path) {
    if (path == null) throw new NullPointerException("The path of the image(s) to save should not be null");
    if (path.length() == 0) throw new IllegalArgumentException("The path of the image(s) to save should not be empty");
  }
}
