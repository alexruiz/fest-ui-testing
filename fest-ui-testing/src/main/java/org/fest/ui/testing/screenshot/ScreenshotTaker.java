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

import static java.util.logging.Level.WARNING;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import org.fest.util.VisibleForTesting;

/**
 * Takes a screenshot of the desktop.
 *
 * @author Alex Ruiz
 */
public class ScreenshotTaker {

  private static final ScreenshotTaker INSTANCE = new ScreenshotTaker();

  /**
   * Returns the singleton instance of this class.
   * @return the singleton instance of this class.
   */
  public static ScreenshotTaker instance() {
    return INSTANCE;
  }

  private static Logger logger = Logger.getAnonymousLogger();

  private final ImageFileWriter writer;
  private final ImageFilePathValidator pathValidator;
  private final Display display;
  private final Robot robot;

  private ScreenshotTaker() {
    this(new ImageFileWriter(), ImageFilePathValidator.instance(), Display.instance());
  }

  @VisibleForTesting
  ScreenshotTaker(ImageFileWriter writer, ImageFilePathValidator pathValidator, Display display) {
    this.writer = writer;
    this.pathValidator = pathValidator;
    this.display = display;
    this.robot = robot(display);
  }

  private static Robot robot(Display display) {
    Robot robot = null;
    try {
      robot = display.newRobotInPrimary();
    } catch (Throwable t) {
      logger.log(WARNING, "Unable to create an AWT Robot", t);
    }
    return robot;
  }

  /**
   * Takes a screenshot of the desktop and saves it as a PNG file.
   * @param path the path of the file to save the screenshot to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   */
  public void saveDesktopAsPng(String path) {
    pathValidator.validate(path);
    if (robot == null) return;
    try {
      writer.writeAsPng(screenshotOfDesktop(), path);
    } catch (Throwable t) {
      logger.log(WARNING, "Unable to take the screenshot and save it as a file", t);
    }
  }

  private BufferedImage screenshotOfDesktop() {
    Rectangle r = new Rectangle(display.sizeOfPrimary());
    return robot.createScreenCapture(r);
  }
}
