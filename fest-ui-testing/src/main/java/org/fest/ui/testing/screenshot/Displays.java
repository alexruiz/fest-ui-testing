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

import static java.util.Collections.unmodifiableList;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import org.fest.util.VisibleForTesting;

/**
 * Represents all the displays a system may have.
 *
 * @author Alex Ruiz
 */
public class Displays {

  private final List<Display> displays = new ArrayList<Display>();

  @VisibleForTesting Displays(RobotFactory robotFactory, GraphicsEnvironment environment) throws AWTException {
    for (GraphicsDevice screen : environment.getScreenDevices()) 
      displays.add(new Display(robotFactory, screen));
  }
  
  @VisibleForTesting Displays(List<Display> displays) {
    this.displays.addAll(displays);
  }
  
  public BufferedImage[] desktopScreenshots() {
    int displayCount = displays.size();
    BufferedImage[] images = new BufferedImage[displayCount];
    for (int i = 0; i < displayCount; i++)
      images[i] = displays.get(i).desktopScreenshot();
    return images;
  }
  
  @VisibleForTesting List<Display> displays() {
    return unmodifiableList(displays);
  }
}
