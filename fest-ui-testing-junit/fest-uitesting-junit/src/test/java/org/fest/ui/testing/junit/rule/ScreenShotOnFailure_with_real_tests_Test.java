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
package org.fest.ui.testing.junit.rule;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static javax.imageio.ImageIO.read;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.ui.testing.junit.rule.ScreenshotOnFailure.newRule;
import static org.fest.util.Files.delete;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import org.fest.ui.testing.junit.category.GuiTest;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runner.*;

/**
 * Tests <code>{@link ScreenshotOnFailure}</code> using a real test class.
 *
 * @author Alex Ruiz
 */
public class ScreenShotOnFailure_with_real_tests_Test {

  private JUnitCore junit;
  private ScreenshotsFolderFactory folderFactory;
  
  @Before public void setUp() throws IOException {
    folderFactory = ScreenshotsFolderFactory.instance();
    delete(folderFactory.createFolderForScreenshots());
    junit = new JUnitCore();
  }

  @Test public void should_take_screenshot_when_test_fails() throws IOException {
    Result result = junit.run(MyTest.class);
    assertThat(result.wasSuccessful()).isFalse();
    File folder = folderFactory.createFolderForScreenshots();
    File[] screenshotFiles = folder.listFiles();
    GraphicsDevice[] screens = allScreens();
    int displayCount = screens.length;
    assertThat(screenshotFiles).hasSize(displayCount);
    for (int i = 0; i < displayCount; i++) {
      BufferedImage screenshot = read(screenshotFiles[i]);
      assertThat(screenshot).hasSize(sizeOf(screens[i]));
    }
  }

  private static GraphicsDevice[] allScreens() {
    return getLocalGraphicsEnvironment().getScreenDevices();
  }

  private static Dimension sizeOf(GraphicsDevice screen) {
    return screen.getDefaultConfiguration().getBounds().getSize();
  }
  
  @Category(GuiTest.class)
  public static class MyTest {
    
    @Rule public ScreenshotOnFailure screenshotOnFailure = newRule();
    
    @Test public void shouldFailOnPurpose() {
      throw new RuntimeException("Thrown on purpose");
    }
  }
}
