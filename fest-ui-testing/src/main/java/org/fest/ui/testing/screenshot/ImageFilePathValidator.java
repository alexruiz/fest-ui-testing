/*
 * Created on Nov 10, 2010
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

import static org.fest.ui.testing.screenshot.ImageFormats.PNG;

/**
 * Validates the path of an image file.
 *
 * @author Alex Ruiz
 */
final class ImageFilePathValidator {

  private static ImageFilePathValidator INSTANCE = new ImageFilePathValidator();

  static ImageFilePathValidator instance() {
    return INSTANCE;
  }

  void validate(String imageFilePath) {
    if (imageFilePath == null) throw nullPath();
    if (!imageFilePath.endsWith(PNG)) throw missingPngExtension(imageFilePath);
  }

  private RuntimeException nullPath() {
    return new NullPointerException("The path of the image to save should not be null");
  }

  private RuntimeException missingPngExtension(String path) {
    String message = String.format("The path '%s' should end with '.%s'", path, PNG);
    return new IllegalArgumentException(message);
  }

  private ImageFilePathValidator() {}
}
