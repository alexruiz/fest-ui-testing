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

/**
 * Takes a screenshot of the desktop.
 *
 * @author Alex Ruiz
 */
public class ScreenshotTaker {

  /**
   * Takes a screenshot of the desktop and saves it as a PNG file.
   * @param path the path of the file to save the screenshot to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   */
  /*
   * @throws ImageException if the given file path belongs to a non-empty directory.
   * @throws ImageException if an I/O error prevents the image from being saved as a file.
   */
  public void saveDesktopAsPng(String path) {
    // TODO implement
  }
}
