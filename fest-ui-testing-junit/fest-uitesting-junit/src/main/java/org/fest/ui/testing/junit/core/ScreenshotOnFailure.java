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
package org.fest.ui.testing.junit.core;

import java.io.File;
import java.lang.reflect.Method;

import org.fest.ui.testing.screenshot.ScreenshotTaker;
import org.fest.util.VisibleForTesting;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Method rule that takes a screenshot of the desktop if a test belonging to the <code>{@link GuiTest}</code> category
 * fails.
 *
 * @author Alex Ruiz
 */
public class ScreenshotOnFailure implements MethodRule {

  @VisibleForTesting final ScreenshotFilePathCreator pathCreator;

  public static ScreenshotOnFailure newRule() {
    ScreenshotFilePathCreator pathCreator = null;
    try {
      File parentFolder = ScreenshotsFolderCreator.instance().createScreenshotsFolder();
      pathCreator = new ScreenshotFilePathCreator(parentFolder);
    } catch (Exception e) {
      ignore(e);
    }
    return new ScreenshotOnFailure(pathCreator);
  }

  @VisibleForTesting ScreenshotOnFailure(ScreenshotFilePathCreator pathCreator) {
    this.pathCreator = pathCreator;
  }

  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    if (pathCreator == null) return base;
    return new ScreenshotOnFailureStatement(base, method, pathCreator);
  }

  @VisibleForTesting static class ScreenshotOnFailureStatement extends Statement {
    final Statement base;
    final FrameworkMethod method;

    GuiTestFilter guiTestFilter = GuiTestFilter.instance();
    ScreenshotTaker screenshotTaker = new ScreenshotTaker();
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
        ignore(e);
      }
    }
  }

  private static void ignore(Throwable t) {
    t.printStackTrace(); // not sure what we should do here.
  }
}
