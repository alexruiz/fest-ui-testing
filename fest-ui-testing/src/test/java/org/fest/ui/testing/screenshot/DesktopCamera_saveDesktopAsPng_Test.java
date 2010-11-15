/*
 * Created on Nov 14, 2010
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

import static org.fest.ui.testing.test.ExpectedException.none;
import static org.fest.util.Arrays.array;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.fest.ui.testing.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link DesktopCamera#saveDesktopAsPng(String)}</code>.
 *
 * @author Alex Ruiz
 */
public class DesktopCamera_saveDesktopAsPng_Test {

  @Rule public ExpectedException thrown = none();

  private ImageFileWriter writer;
  private Displays displays;
  private DesktopCamera camera;

  @Before public void setUp() {
    writer = mock(ImageFileWriter.class);
    displays = mock(Displays.class);
    camera = new DesktopCamera(writer, displays);
  }

  @Test public void should_throw_error_if_path_is_null() {
    thrown.expectNullPointerException("The path of the image(s) to save should not be null");
    camera.saveDesktopAsPng(null);
  }

  @Test public void should_throw_error_if_path_is_empty() {
    expectErrorIfPathIsEmpty();
    camera.saveDesktopAsPng("");
  }

  @Test public void should_throw_error_if_path_contains_spaces_only() {
    expectErrorIfPathIsEmpty();
    camera.saveDesktopAsPng("        ");
  }

  private void expectErrorIfPathIsEmpty() {
    thrown.expectIllegalArgumentException("The path of the image(s) to save should not be empty");
  }

  @Test public void should_save_one_screenshot_if_there_is_only_one_display() throws IOException {
    BufferedImage screenshot = mock(BufferedImage.class);
    when(displays.desktopScreenshots()).thenReturn(array(screenshot));
    camera.saveDesktopAsPng("/tmp/image");
    verify(writer).writeAsPng(screenshot, "/tmp/image.png");
  }

  @Test public void should_save_one_screenshot_per_display() throws IOException {
    BufferedImage screenshot1 = mock(BufferedImage.class);
    BufferedImage screenshot2 = mock(BufferedImage.class);
    when(displays.desktopScreenshots()).thenReturn(array(screenshot1, screenshot2));
    camera.saveDesktopAsPng("/tmp/image");
    verify(writer).writeAsPng(screenshot1, "/tmp/image0.png");
    verify(writer).writeAsPng(screenshot2, "/tmp/image1.png");
  }
}
