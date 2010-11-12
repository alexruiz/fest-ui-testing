/*
 * Created on Nov 11, 2010
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

import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getAnonymousLogger;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.fest.ui.testing.junit.category.GuiTestFilter;
import org.fest.ui.testing.screenshot.ScreenshotTaker;
import org.fest.util.VisibleForTesting;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Wrapper statement that takes a screenshot of the desktop if the real, wrapped {@code Statement} fails during its
 * execution.
 *
 * @author Alex Ruiz
 */
class ScreenshotOnFailureStatement extends Statement {

  private static Logger logger = getAnonymousLogger();

  @VisibleForTesting final Statement base;
  @VisibleForTesting final FrameworkMethod method;

  @VisibleForTesting GuiTestFilter guiTestFilter = GuiTestFilter.instance();
  @VisibleForTesting ScreenshotTaker screenshotTaker = ScreenshotTaker.instance();

  private final ScreenshotFilePathCreator pathCreator;

  ScreenshotOnFailureStatement(Statement base, FrameworkMethod method, ScreenshotFilePathCreator pathCreator) {
    this.base = base;
    this.method = method;
    this.pathCreator = pathCreator;
  }

  @Override public void evaluate() throws Throwable {
    try {
      base.evaluate();
    } catch (Throwable t) {
      takeScreenshotIfApplicable();
      throw t;
    }
  }

  private void takeScreenshotIfApplicable() {
    try {
      if (!guiTestFilter.isGuiTest(method)) return;
      Method realMethod = method.getMethod();
      String path = pathCreator.filePathFrom(realMethod.getDeclaringClass(), realMethod);
      screenshotTaker.saveDesktopAsPng(path);
    } catch (Exception e) {
      logger.log(WARNING, "Unable to take screenshot of the desktop", e);
    }
  }
}