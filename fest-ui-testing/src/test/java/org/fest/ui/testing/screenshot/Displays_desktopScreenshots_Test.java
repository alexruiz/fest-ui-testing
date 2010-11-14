/*
 * Created on Nov 13, 2010
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Collections.list;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;

import org.junit.*;

/**
 * Tests for <code>{@link Displays#desktopScreenshots()}</code>.
 *
 * @author Alex Ruiz
 */
public class Displays_desktopScreenshots_Test {

  private Display display1;
  private Display display2;
  private BufferedImage screenshot1;
  private BufferedImage screenshot2;
  
  private Displays displays;
  
  @Before public void setUp() {
    display1 = mock(Display.class);
    display2 = mock(Display.class);
    screenshot1 = mock(BufferedImage.class);
    screenshot2 = mock(BufferedImage.class);
    displays = new Displays(list(display1, display2));
  }
  
  @Test public void should_return_all_screenshots_from_displays() {
    when(display1.desktopScreenshot()).thenReturn(screenshot1);
    when(display2.desktopScreenshot()).thenReturn(screenshot2);
    BufferedImage[] screenshots = displays.desktopScreenshots();
    assertThat(screenshots).containsOnly(screenshot1, screenshot2);
  }
}
