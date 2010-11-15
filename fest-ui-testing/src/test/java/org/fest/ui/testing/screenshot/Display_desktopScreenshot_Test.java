/*
 * Created on Nov 12, 2010
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

import java.awt.*;
import java.awt.image.BufferedImage;

import org.junit.*;

/**
 * Tests for <code>{@link Display#desktopScreenshot()}</code>.
 *
 * @author Alex Ruiz
 */
public class Display_desktopScreenshot_Test {

  private GraphicsDevice device;
  private Display display;
  
  @Before public void setUp() throws AWTException {
    device = getLocalGraphicsEnvironment().getDefaultScreenDevice();
    display = new Display(RobotFactory.instance(), device);
  }
  
  @Test public void should_take_screenshot_of_desktop() {
    BufferedImage screenshot = display.desktopScreenshot();
    assertThat(screenshot).hasSize(screenSize());
  }

  private Dimension screenSize() {
    return device.getDefaultConfiguration().getBounds().getSize();
  }
}
