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

import java.awt.*;
import java.awt.image.BufferedImage;

import org.fest.util.VisibleForTesting;

/**
 * @author Alex Ruiz
 */
class Display {
  
  private final Robot robot;
  private final GraphicsDevice device;

  Display(GraphicsDevice device) throws AWTException {
    this(new Robot(device), device);
  }
  
  @VisibleForTesting Display(Robot robot, GraphicsDevice device) {
    this.robot = robot;
    this.device = device;
  }

  BufferedImage desktopScreenshot() {
    Rectangle r = device.getDefaultConfiguration().getBounds();
    return robot.createScreenCapture(r);
  }
}