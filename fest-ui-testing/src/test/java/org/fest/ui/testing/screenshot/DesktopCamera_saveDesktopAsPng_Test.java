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
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.util.Collections.isEmpty;
import static org.fest.util.Files.*;
import static org.fest.util.Strings.concat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import org.junit.*;

/**
 * Tests for <code>{@link DesktopCamera#saveDesktopAsPng(String)}</code>.
 *
 * @author Alex Ruiz
 */
public class DesktopCamera_saveDesktopAsPng_Test {

  private List<String> filePaths;
  private DesktopCamera camera;
  
  @Before public void setUp() {
    camera = DesktopCamera.instance();
  }

  @After public void tearDown() {
    if (isEmpty(filePaths)) return;
    for (String path : filePaths) delete(new File(path));
  }
  
  @Test public void should_take_screenshot_of_desktop() throws Exception {
    String folder = concat(temporaryFolderPath(), "img");
    filePaths = camera.saveDesktopAsPng(folder);
    GraphicsDevice[] displays = getLocalGraphicsEnvironment().getScreenDevices();
    int displayCount = displays.length;
    assertThat(filePaths).hasSize(displayCount);
    for (int i = 0; i < displayCount; i++) {
      String imageFilePath = filePaths.get(i);
      BufferedImage screenshot = read(imageFilePath);
      assertThat(screenshot).hasSize(sizeOf(displays[i]));
    }
  }

  private static Dimension sizeOf(GraphicsDevice display) {
    return display.getDefaultConfiguration().getBounds().getSize();
  }
}
