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
package org.fest.ui.testing.junit.rule;

import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getAnonymousLogger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.fest.ui.testing.junit.category.GuiTest;
import org.fest.ui.testing.junit.category.GuiTestFilter;
import org.fest.ui.testing.screenshot.DesktopCamera;
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

  private static Logger logger = getAnonymousLogger();

  @VisibleForTesting final FilePathFactory pathFactory;

  public static ScreenshotOnFailure newRule() {
    FilePathFactory pathFactory = null;
    try {
      File parentFolder = ScreenshotsFolderFactory.instance().createFolderForScreenshots();
      pathFactory = new FilePathFactory(parentFolder);
    } catch (Throwable t) {
      logger.log(WARNING, "Unable to create the folder where to store screenshots", t);
    }
    return new ScreenshotOnFailure(pathFactory);
  }

  @VisibleForTesting ScreenshotOnFailure(FilePathFactory pathFactory) {
    this.pathFactory = pathFactory;
  }

  /**
   * Modifies the given method-running {@code {@link Statement}} to take a screenshot of the desktop if the execution of
   * the test method fails.
   * @param base the {@code Statement} to be modified.
   * @param method the method to be run.
   * @param target the object on with the method will be run.
   * @return a new statement, which may be the same as {@code base} if it is not possible to create the folder where
   * the screenshot will be stored or a wrapper around {@code base} that takes a screenshot of the desktop if the
   * execution of the test method fails.
   */
  public Statement apply(Statement baseStatement, FrameworkMethod method, Object target) {
    if (pathFactory == null) return baseStatement;
    return new ScreenshotOnFailureStatement(baseStatement, method);
  }

  class ScreenshotOnFailureStatement extends Statement {
    private final Statement baseStatement;
    private final FrameworkMethod method;

    @VisibleForTesting GuiTestFilter guiTestFilter = GuiTestFilter.instance();
    @VisibleForTesting DesktopCamera desktopCamera = DesktopCamera.instance();

    ScreenshotOnFailureStatement(Statement baseStatement, FrameworkMethod method) {
      this.baseStatement = baseStatement;
      this.method = method;
    }

    @Override public void evaluate() throws Throwable {
      try {
        baseStatement.evaluate();
      } catch (Throwable t) {
        takeScreenshotIfApplicable();
        throw t;
      }
    }

    private void takeScreenshotIfApplicable() {
      try {
        if (!guiTestFilter.isGuiTest(method)) return;
        Method realMethod = method.getMethod();
        String path = pathFactory.deriveFilePathFrom(realMethod.getDeclaringClass(), realMethod);
        desktopCamera.saveDesktopAsPng(path);
      } catch (Throwable t) {
        logger.log(WARNING, "Unable to take screenshot of the desktop", t);
      }
    }
  }
}
