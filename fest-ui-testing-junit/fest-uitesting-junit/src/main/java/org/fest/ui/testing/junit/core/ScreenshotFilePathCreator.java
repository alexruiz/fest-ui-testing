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
package org.fest.ui.testing.junit.core;

import static java.io.File.separator;
import static org.fest.ui.testing.screenshot.ImageFormats.PNG;
import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Strings.concat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Creates the path of the file where a screenshot of the desktop will be saved when a GUI test fails.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ScreenshotFilePathCreator {

  private final File parentFolder;

  ScreenshotFilePathCreator(File parentFolder) {
    this.parentFolder = parentFolder;
  }

  String filePathFrom(Class<?> type, Method method) throws IOException {
    String testName = testNameFrom(type, method);
    return concat(parentFolder.getCanonicalPath(), separator, testName, ".", PNG);
  }

  private static String testNameFrom(Class<?> type, Method method) {
    return concat(type.getName(), ".", method.getName(), format(method.getParameterTypes()));
  }

  private static String format(Class<?>[] parameterTypes) {
    if (isEmpty(parameterTypes)) return "";
    StringBuilder buffer = new StringBuilder("(");
    for (int i = 0; i < parameterTypes.length; i++) {
      buffer.append(parameterTypes[i].getName());
      if (i < parameterTypes.length - 1) buffer.append(", ");
    }
    buffer.append(")");
    return buffer.toString();
  }
}
